package org.example.luvbackend.service;

import java.util.List;
import java.util.UUID;

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
	 */
	@Transactional
	public HomeWorshipResponseDto createHomeWorship(HomeWorshipCreateForm form) {
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.HOMEWORSHIPS);
		String hashedPassword = passwordEncoder.encode(form.getPassword());
		return HomeWorshipResponseDto.from(homeworshipRepository.save(HomeWorship.of(form, imageUrls, hashedPassword)));
	}

	/**
	 * 단건 가정예배 수정
	 */
	@Transactional
	public HomeWorshipResponseDto updateHomeWorship(String id, HomeWorshipUpdateForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(id);
		verifyPassword(form.getPassword(), homeworship.getPassword());

		List<String> uploadedUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.HOMEWORSHIPS);
		List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), uploadedUrls);

		String date = form.getDate() != null ? form.getDate().toString() : null;
		homeworship.update(date, form.getTitle(), form.getContent(), mergedImageUrls);
		return HomeWorshipResponseDto.from(homeworshipRepository.save(homeworship));
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
		HomeWorshipComment comment = HomeWorshipComment.builder()
			.id(UUID.randomUUID().toString())
			.userName(form.getUserName())
			.password(hashedPassword)
			.content(form.getContent())
			.build();
		homeworship.addComment(comment);
		return HomeWorshipResponseDto.from(homeworshipRepository.save(homeworship));
	}

	/**
	 * 가정예배 댓글 삭제
	 */
	@Transactional
	public HomeWorshipResponseDto deleteComment(String homeWorshipId, String commentId, CommentDeleteForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(homeWorshipId);
		HomeWorshipComment comment = homeworship.getComments().stream()
			.filter(c -> c.getId().equals(commentId))
			.findFirst()
			.orElseThrow(() -> new HomeWorshipException(HomeworshipExceptionCode.NOT_FOUND_COMMENT));
		verifyPassword(form.getPassword(), comment.getPassword());
		homeworship.removeComment(commentId);
		return HomeWorshipResponseDto.from(homeworshipRepository.save(homeworship));
	}

	private void verifyPassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new HomeWorshipException(HomeworshipExceptionCode.INVALID_PASSWORD);
		}
	}

}