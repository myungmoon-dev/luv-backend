package org.example.luvbackend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.luvbackend.common.util.FileUtils;
import org.example.luvbackend.dto.education.CoreMinistryInput;
import org.example.luvbackend.dto.education.EducationForm;
import org.example.luvbackend.dto.education.EducationResponseDto;
import org.example.luvbackend.entity.education.CoreMinistry;
import org.example.luvbackend.entity.education.Education;
import org.example.luvbackend.exception.education.EducationException;
import org.example.luvbackend.exception.education.EducationExceptionCode;
import org.example.luvbackend.repository.EducationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	private final EducationRepository educationRepository;
	private final AwsS3Service awsS3Service;
	private final ObjectMapper objectMapper;

	@Transactional(readOnly = true)
	public List<EducationResponseDto> getEducations() {
		return educationRepository.findAll().stream()
			.sorted(Comparator.comparing(
				Education::getOrder,
				Comparator.nullsLast(Comparator.naturalOrder())
			))
			.map(EducationResponseDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public EducationResponseDto getEducationByType(String type) {
		return educationRepository.findByType(type)
			.map(EducationResponseDto::from)
			.orElseThrow(() -> new EducationException(EducationExceptionCode.NOT_FOUND_EDUCATION));
	}

	@Transactional
	public EducationResponseDto createEducation(EducationForm form) {
		if (educationRepository.existsByType(form.getType())) {
			throw new EducationException(EducationExceptionCode.DUPLICATE_TYPE);
		}

		String heroImageUrl = form.getHeroImage() != null && !form.getHeroImage().isEmpty()
			? awsS3Service.uploadFile(form.getHeroImage(), buildKey(form.getType(), "hero", form.getHeroImage()))
			: null;

		List<String> imageUrls = uploadImages(form.getType(), "imgs", form.getImages());
		List<CoreMinistry> coreMinistries = buildCoreMinistries(form);

		Education education = Education.builder()
			.type(form.getType())
			.slug(form.getSlug())
			.department(form.getDepartment())
			.heroImageUrl(heroImageUrl)
			.heroImgClass(form.getHeroImgClass())
			.imageUrls(imageUrls)
			.introduction(form.getIntroduction())
			.target(form.getTarget())
			.time(form.getTime())
			.place(form.getPlace())
			.meetingTime(form.getMeetingTime())
			.coreMinistries(coreMinistries)
			.order(parseOrder(form.getOrder()))
			.build();

		return EducationResponseDto.from(educationRepository.save(education));
	}

	@Transactional
	public EducationResponseDto updateEducation(String id, EducationForm form) {
		Education education = educationRepository.findByIdOrElseThrow(id);

		// 배너 이미지 교체
		String heroImageUrl = null;
		MultipartFile heroImage = form.getHeroImage();
		if (heroImage != null && !heroImage.isEmpty()) {
			if (education.getHeroImageUrl() != null) {
				awsS3Service.deleteFiles(List.of(education.getHeroImageUrl()));
			}
			heroImageUrl = awsS3Service.uploadFile(heroImage, buildKey(education.getType(), "hero", heroImage));
		}

		// 상단 이미지 교체 (기존 유지 + 새로 추가)
		List<String> finalImageUrls = mergeImageUrls(
			education.getImageUrls(),
			form.getExistingImageUrls(),
			form.getImages(),
			education.getType()
		);

		// 핵심사역 처리
		List<CoreMinistry> coreMinistries = buildUpdatedCoreMinistries(education, form);

		education.update(
			form.getSlug(),
			form.getDepartment(),
			heroImageUrl,
			form.getHeroImgClass(),
			finalImageUrls,
			form.getIntroduction(),
			form.getTarget(),
			form.getTime(),
			form.getPlace(),
			form.getMeetingTime(),
			coreMinistries,
			parseOrder(form.getOrder())
		);

		return EducationResponseDto.from(educationRepository.save(education));
	}

	@Transactional
	public void deleteEducation(String id) {
		Education education = educationRepository.findByIdOrElseThrow(id);
		List<String> urls = new ArrayList<>();
		if (education.getHeroImageUrl() != null) urls.add(education.getHeroImageUrl());
		urls.addAll(education.getImageUrls());
		education.getCoreMinistries().forEach(c -> {
			if (c.getImageUrl() != null) urls.add(c.getImageUrl());
		});
		if (!urls.isEmpty()) awsS3Service.deleteFiles(urls);
		educationRepository.delete(education);
	}

	private List<String> uploadImages(String type, String prefix, List<MultipartFile> files) {
		if (files == null || files.isEmpty()) return new ArrayList<>();
		List<String> urls = new ArrayList<>();
		for (MultipartFile file : files) {
			if (file == null || file.isEmpty()) continue;
			urls.add(awsS3Service.uploadFile(file, buildKey(type, prefix, file)));
		}
		return urls;
	}

	private List<String> mergeImageUrls(List<String> existingInDb, List<String> existingFromForm,
		List<MultipartFile> newFiles, String type) {
		List<String> result = new ArrayList<>();

		// 폼에서 유지하라고 보낸 URL만 남김
		if (existingFromForm != null && !existingFromForm.isEmpty()) {
			result.addAll(existingFromForm);
			// 유지 안 되는 기존 이미지는 S3에서 삭제
			List<String> toDelete = existingInDb.stream()
				.filter(url -> !existingFromForm.contains(url))
				.toList();
			if (!toDelete.isEmpty()) awsS3Service.deleteFiles(toDelete);
		} else if (existingFromForm == null) {
			// 전달 안 했으면 기존 유지
			result.addAll(existingInDb);
		} else {
			// 빈 배열로 보내면 전부 삭제
			if (!existingInDb.isEmpty()) awsS3Service.deleteFiles(existingInDb);
		}

		// 새 이미지 추가
		result.addAll(uploadImages(type, "imgs", newFiles));
		return result;
	}

	private List<CoreMinistry> buildCoreMinistries(EducationForm form) {
		List<CoreMinistryInput> inputs = parseCoreMinistries(form.getCoreMinistriesJson());
		List<CoreMinistry> result = new ArrayList<>();
		for (int i = 0; i < inputs.size(); i++) {
			CoreMinistryInput input = inputs.get(i);
			String imageUrl = uploadCoreMinistryImage(form, i, input.getImageUrl());
			result.add(CoreMinistry.builder()
				.titleKr(input.getTitleKr())
				.titleEn(input.getTitleEn())
				.description(input.getDescription())
				.imageUrl(imageUrl)
				.imgClass(input.getImgClass())
				.build());
		}
		return result;
	}

	private List<CoreMinistry> buildUpdatedCoreMinistries(Education education, EducationForm form) {
		// JSON 없으면 기존 유지
		if (form.getCoreMinistriesJson() == null || form.getCoreMinistriesJson().isBlank()) {
			return education.getCoreMinistries();
		}

		List<CoreMinistryInput> inputs = parseCoreMinistries(form.getCoreMinistriesJson());
		List<CoreMinistry> result = new ArrayList<>();
		List<String> oldUrls = education.getCoreMinistries().stream()
			.map(CoreMinistry::getImageUrl)
			.filter(url -> url != null)
			.toList();
		List<String> keptUrls = new ArrayList<>();

		for (int i = 0; i < inputs.size(); i++) {
			CoreMinistryInput input = inputs.get(i);
			String imageUrl = uploadCoreMinistryImage(form, i, input.getImageUrl());
			if (imageUrl != null) keptUrls.add(imageUrl);
			result.add(CoreMinistry.builder()
				.titleKr(input.getTitleKr())
				.titleEn(input.getTitleEn())
				.description(input.getDescription())
				.imageUrl(imageUrl)
				.imgClass(input.getImgClass())
				.build());
		}

		// 더 이상 사용되지 않는 기존 이미지 삭제
		List<String> toDelete = oldUrls.stream().filter(url -> !keptUrls.contains(url)).toList();
		if (!toDelete.isEmpty()) awsS3Service.deleteFiles(toDelete);
		return result;
	}

	/** 인덱스 i에 해당하는 핵심사역 이미지를 업로드, 없으면 입력의 기존 imageUrl 그대로 반환 */
	private String uploadCoreMinistryImage(EducationForm form, int index, String existingImageUrl) {
		List<MultipartFile> images = form.getCoreMinistryImages();
		List<Integer> indexes = form.getCoreMinistryImageIndexes();
		if (images == null || indexes == null) return existingImageUrl;

		for (int j = 0; j < indexes.size() && j < images.size(); j++) {
			if (indexes.get(j) == index) {
				MultipartFile file = images.get(j);
				if (file != null && !file.isEmpty()) {
					return awsS3Service.uploadFile(file, buildKey(form.getType(), "core" + index, file));
				}
			}
		}
		return existingImageUrl;
	}

	private List<CoreMinistryInput> parseCoreMinistries(String json) {
		if (json == null || json.isBlank()) return new ArrayList<>();
		try {
			return objectMapper.readValue(json, new TypeReference<>() {});
		} catch (Exception e) {
			throw new EducationException(EducationExceptionCode.INVALID_CORE_MINISTRIES);
		}
	}

	private Integer parseOrder(String order) {
		if (order == null || order.isBlank()) return null;
		try {
			return Integer.parseInt(order.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String buildKey(String type, String prefix, MultipartFile file) {
		String now = LocalDateTime.now().format(FORMATTER);
		String ext = FileUtils.extractExtension(file.getOriginalFilename());
		return "education/" + type + "/" + prefix + "_" + now + "_" + System.nanoTime() + ext;
	}
}
