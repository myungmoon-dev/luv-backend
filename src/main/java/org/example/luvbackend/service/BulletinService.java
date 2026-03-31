package org.example.luvbackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.example.luvbackend.dto.bulletin.BulletinResponseDto;
import org.example.luvbackend.dto.bulletin.BulletinUpdateForm;
import org.example.luvbackend.dto.bulletin.BulletinUploadForm;
import org.example.luvbackend.entity.bulletin.Bulletin;
import org.example.luvbackend.repository.BulletinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulletinService {
	private final BulletinRepository bulletinRepository;
	private final AwsS3Service awsS3Service;
	private final PdfService pdfService;

	/**
	 * 다건 페이징 주보 조회 (year+month 필터 optional)
	 */
	@Transactional(readOnly = true)
	public Page<BulletinResponseDto> getBulletins(int page, int size, String year, String month) {
		Pageable pageable = PageRequest.of(page, size);
		if (year != null && month != null) {
			return bulletinRepository.findAllByDateStartingWithOrderByDateDesc(year + "-" + month, pageable)
				.map(BulletinResponseDto::from);
		}

		return bulletinRepository.findAllByOrderByDateDesc(pageable)
			.map(BulletinResponseDto::from);
	}

	/**
	 * 단건 주보 조회
	 */
	@Transactional(readOnly = true)
	public BulletinResponseDto getBulletin(String id) {
		return BulletinResponseDto.from(bulletinRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 주보 생성
	 */
	@Transactional
	public BulletinResponseDto createBulletin(BulletinUploadForm form) {
		log.info("주보 생성 - title: {}, date: {}, filename: {}", form.getTitle(), form.getDate(), form.getPdf().getOriginalFilename());

		// 동일날짜에 이미 존재하면 삭제 후 재업로드
		bulletinRepository.findByDate(form.getDate().toString())
			.ifPresent(existing -> {
				log.info("동일 날짜 주보 존재 - 기존 데이터 삭제: {}", existing.getId());
				awsS3Service.deleteFiles(existing.getImageUrls()); // 기존 S3 이미지 삭제
				bulletinRepository.delete(existing);  // 기존 DB 데이터 삭제
		});

		List<String> imageUrls = pdfService.convertAndUpload(form.getPdf(), form.getDate()); // PDFBOX 사용하여 IMAGE 추출
		return BulletinResponseDto.from(bulletinRepository.save(Bulletin.of(form, imageUrls)));
	}

	/**
	 * 단건 주보 수정 (날짜만 / PDF만 / 날짜+PDF)
	 */
	@Transactional
	public BulletinResponseDto updateBulletin(String id, BulletinUpdateForm form) {
		Bulletin bulletin = bulletinRepository.findByIdOrElseThrow(id);

		if (form.getDate() != null) {
			bulletin.updateDate(form.getDate().toString());
		}

		if (form.getPdf() != null && !form.getPdf().isEmpty()) {
			awsS3Service.deleteFiles(bulletin.getImageUrls());

			LocalDate date = form.getDate() != null ? form.getDate() : LocalDate.parse(bulletin.getDate());
			List<String> imageUrls = pdfService.convertAndUpload(form.getPdf(), date);

			bulletin.updateImageUrls(imageUrls);
		}

		return BulletinResponseDto.from(bulletinRepository.save(bulletin));
	}

	/**
	 * 단건 주보 삭제
	 */
	@Transactional
	public void deleteBulletin(String id) {
		log.info("주보 삭제 - id: {}", id);
		Bulletin fromDB = bulletinRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls());
		bulletinRepository.delete(fromDB);
	}

	/**
	 * 다건 주보 삭제
	 */
	@Transactional
	public void deleteBulletins(List<String> ids) {
		log.info("주보 일괄 삭제 - ids: {}", ids);
		List<Bulletin> bulletins = bulletinRepository.findAllById(ids);

		bulletins.forEach(
			bulletin -> awsS3Service.deleteFiles(bulletin.getImageUrls())); // 이미지 삭제
		bulletinRepository.deleteAll(bulletins); // DB 삭제
	}

	/**
	 * 존재하는 주보 연도/월 추출 (ex. { "2026" : ["01", "02", "03"], ... }
	 */
	@Transactional(readOnly = true)
	public Map<String, List<String>> getAvailableDates() {
		List<Bulletin> bulletins = bulletinRepository.findAllYearAndDates();

		// "2026-03-29" → 연도/월 추출
		Map<String, List<String>> result = new LinkedHashMap<>();

		bulletins.stream()
			.map(b -> b.getDate().substring(0, 7)) // "2026-03"
			.distinct()
			.sorted(Comparator.reverseOrder())
			.forEach(yearMonth -> {
				String year = yearMonth.substring(0, 4);
				String month = yearMonth.substring(5, 7);
				result.computeIfAbsent(year,
					ifYearKeyNotExist -> new ArrayList<>()).add(month);
			});

		return result;
	}

}
