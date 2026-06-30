package org.example.luvbackend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.example.luvbackend.common.util.FileUtils;
import org.example.luvbackend.dto.education.EducationHomeForm;
import org.example.luvbackend.dto.education.EducationHomeResponseDto;
import org.example.luvbackend.entity.education.EducationHome;
import org.example.luvbackend.entity.education.EducationHomeCoreValue;
import org.example.luvbackend.entity.education.EducationHomeVision;
import org.example.luvbackend.exception.education.EducationException;
import org.example.luvbackend.exception.education.EducationExceptionCode;
import org.example.luvbackend.repository.EducationHomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationHomeService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	private final EducationHomeRepository repository;
	private final AwsS3Service awsS3Service;
	private final ObjectMapper objectMapper;

	@Transactional(readOnly = true)
	public EducationHomeResponseDto get() {
		EducationHome home = repository.findAll().stream().findFirst().orElse(null);
		if (home == null) return null;
		return EducationHomeResponseDto.from(home);
	}

	@Transactional
	public EducationHomeResponseDto upsert(EducationHomeForm form) {
		EducationHome existing = repository.findAll().stream().findFirst().orElse(null);

		List<EducationHomeVision> visions = parseVisions(form.getVisionsJson());
		List<EducationHomeCoreValue> coreValues = parseCoreValues(form.getCoreValuesJson());

		String heroImageUrl = null;
		MultipartFile heroImage = form.getHeroImage();
		if (heroImage != null && !heroImage.isEmpty()) {
			if (existing != null && existing.getHeroImageUrl() != null) {
				awsS3Service.deleteFiles(List.of(existing.getHeroImageUrl()));
			}
			heroImageUrl = awsS3Service.uploadFile(heroImage, buildKey(heroImage));
		}

		if (existing == null) {
			EducationHome home = EducationHome.builder()
				.heroImageUrl(heroImageUrl)
				.heroImgClass(form.getHeroImgClass())
				.heroSubtitle(form.getHeroSubtitle())
				.missionLine1(form.getMissionLine1())
				.missionLine2(form.getMissionLine2())
				.visions(visions)
				.coreValues(coreValues)
				.build();
			return EducationHomeResponseDto.from(repository.save(home));
		}

		existing.update(
			heroImageUrl,
			form.getHeroImgClass(),
			form.getHeroSubtitle(),
			form.getMissionLine1(),
			form.getMissionLine2(),
			visions,
			coreValues
		);
		return EducationHomeResponseDto.from(repository.save(existing));
	}

	private List<EducationHomeVision> parseVisions(String json) {
		if (json == null || json.isBlank()) return null;
		try {
			List<VisionInput> inputs = objectMapper.readValue(json, new TypeReference<>() {});
			return inputs.stream()
				.map(i -> EducationHomeVision.builder()
					.lead(i.lead).emphasis(i.emphasis).bold(i.bold).build())
				.toList();
		} catch (Exception e) {
			throw new EducationException(EducationExceptionCode.INVALID_CORE_MINISTRIES);
		}
	}

	private List<EducationHomeCoreValue> parseCoreValues(String json) {
		if (json == null || json.isBlank()) return null;
		try {
			List<CoreValueInput> inputs = objectMapper.readValue(json, new TypeReference<>() {});
			return inputs.stream()
				.map(i -> EducationHomeCoreValue.builder().n(i.n).title(i.title).build())
				.toList();
		} catch (Exception e) {
			throw new EducationException(EducationExceptionCode.INVALID_CORE_MINISTRIES);
		}
	}

	private String buildKey(MultipartFile file) {
		String now = LocalDateTime.now().format(FORMATTER);
		String ext = FileUtils.extractExtension(file.getOriginalFilename());
		return "education/home/hero_" + now + ext;
	}

	private static class VisionInput {
		public String lead;
		public String emphasis;
		public String bold;
	}

	private static class CoreValueInput {
		public String n;
		public String title;
	}
}
