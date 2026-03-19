package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.homeworship.CommentCreateForm;
import org.example.luvbackend.dto.homeworship.CommentDeleteForm;
import org.example.luvbackend.dto.homeworship.HomeWorshipCreateForm;
import org.example.luvbackend.dto.homeworship.HomeworshipDeleteForm;
import org.example.luvbackend.dto.homeworship.HomeWorshipResponseDto;
import org.example.luvbackend.dto.homeworship.HomeWorshipUpdateForm;
import org.example.luvbackend.entity.homeworship.HomeWorship;
import org.example.luvbackend.entity.homeworship.HomeWorshipComment;
import org.example.luvbackend.exception.homeworship.HomeWorshipException;
import org.example.luvbackend.exception.homeworship.HomeworshipExceptionCode;
import org.example.luvbackend.repository.HomeworshipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeWorshipService {
	private final HomeworshipRepository homeworshipRepository;
	private final AwsS3Service awsS3Service;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * 다건 페이징 가정예배 조회
	 */
	@Transactional(readOnly = true)
	public Page<HomeWorshipResponseDto> getHomeWorships(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return homeworshipRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(HomeWorshipResponseDto::from);
	}

	/**
	 * 단건 가정예배 조회
	 */
	@Transactional(readOnly = true)
	public HomeWorshipResponseDto getHomeWorship(String id) {
		return HomeWorshipResponseDto.from(homeworshipRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 가정예배 생성
	 * S3 업로드는 트랜잭션 밖에서 먼저 수행, DB 저장 실패 시 업로드된 파일 보상 삭제
	 */
	public HomeWorshipResponseDto createHomeWorship(HomeWorshipCreateForm form) {
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.HOMEWORSHIPS);
		try {
			String hashedPassword = passwordEncoder.encode(form.getPassword());
			return HomeWorshipResponseDto.from(homeworshipRepository.save(HomeWorship.of(form, imageUrls, hashedPassword)));
		} catch (Exception e) {
			imageUrls.forEach(awsS3Service::delete);
			throw e;
		}
	}

	/**
	 * 단건 가정예배 수정
	 * S3 업로드는 트랜잭션 밖에서 먼저 수행, DB 저장 실패 시 새로 업로드된 파일 보상 삭제
	 */
	public HomeWorshipResponseDto updateHomeWorship(String id, HomeWorshipUpdateForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(id);
		verifyPassword(form.getPassword(), homeworship.getPassword());

		List<String> uploadedUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.HOMEWORSHIPS);
		try {
			List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), uploadedUrls);
			String date = form.getDate() != null ? form.getDate().toString() : null;
			homeworship.update(date, form.getTitle(), form.getContent(), mergedImageUrls);
			return HomeWorshipResponseDto.from(homeworshipRepository.save(homeworship));
		} catch (Exception e) {
			uploadedUrls.forEach(awsS3Service::delete);
			throw e;
		}
	}

	/**
	 * 단건 가정예배 삭제
	 */
	@Transactional
	public void deleteHomeWorship(String id, HomeworshipDeleteForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(id);
		verifyPassword(form.getPassword(), homeworship.getPassword());
		homeworshipRepository.delete(homeworship);
	}

	/**
	 * 가정예배 댓글 추가
	 */
	@Transactional
	public HomeWorshipResponseDto addComment(String homeWorshipId, CommentCreateForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(homeWorshipId);
		String hashedPassword = passwordEncoder.encode(form.getPassword());
		homeworship.addComment(HomeWorshipComment.of(form.getUserName(), hashedPassword, form.getContent()));
		return HomeWorshipResponseDto.from(homeworshipRepository.save(homeworship));
	}

	/**
	 * 가정예배 댓글 삭제
	 */
	@Transactional
	public void deleteComment(String homeWorshipId, String commentId, CommentDeleteForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(homeWorshipId);
		HomeWorshipComment comment = homeworship.getComments().stream()
			.filter(c -> c.getId().equals(commentId))
			.findFirst()
			.orElseThrow(() -> new HomeWorshipException(HomeworshipExceptionCode.NOT_FOUND_COMMENT));
		verifyPassword(form.getPassword(), comment.getPassword());
		homeworship.removeComment(commentId);
		homeworshipRepository.save(homeworship);
	}

	private void verifyPassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new HomeWorshipException(HomeworshipExceptionCode.INVALID_PASSWORD);
		}
	}

}