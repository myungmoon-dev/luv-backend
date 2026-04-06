package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.bulletin.BulletinResponseDto;
import org.example.luvbackend.dto.bulletin.BulletinUploadForm;
import org.example.luvbackend.entity.bulletin.Bulletin;
import org.example.luvbackend.repository.BulletinRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BulletinService {
	private final BulletinRepository bulletinRepository;
	private final AwsS3Service awsS3Service;

	/**
	 * 다건 페이징 주보 조회
	 */
	@Transactional(readOnly = true)
	public PageResponse<BulletinResponseDto> getBulletins(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return PageResponse.of(
			bulletinRepository.findAllByOrderByDateDesc(pageable)
				.map(BulletinResponseDto::from)
		);
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
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.BULLETINS);
		return BulletinResponseDto.from(bulletinRepository.save(Bulletin.of(form, imageUrls)));
	}

	/**
	 * 단건 주보 삭제
	 */
	@Transactional
	public void deleteBulletin(String id) {
		Bulletin fromDB = bulletinRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls()); // 이미지 삭제
		bulletinRepository.delete(fromDB); // DB 삭제
	}
}
