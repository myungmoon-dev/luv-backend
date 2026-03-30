package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.bulletin.BulletinResponseDto;
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
	 * 다건 페이징 주보 조회
	 */
	@Transactional(readOnly = true)
	public Page<BulletinResponseDto> getBulletins(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
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

		/**
		 * 동일날짜에 이미 존재하면 삭제 후 재업로드
		 */
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
	 * 단건 주보 삭제
	 */
	@Transactional
	public void deleteBulletin(String id) {
		log.info("주보 삭제 - id: {}", id);
		Bulletin fromDB = bulletinRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls());
		bulletinRepository.delete(fromDB);
	}
}
