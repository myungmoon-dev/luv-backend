package org.example.luvbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.example.luvbackend.dto.homeworship.CommentCreateForm;
import org.example.luvbackend.dto.homeworship.CommentDeleteForm;
import org.example.luvbackend.dto.homeworship.HomeWorshipCreateForm;
import org.example.luvbackend.dto.homeworship.HomeWorshipResponseDto;
import org.example.luvbackend.dto.homeworship.HomeWorshipUpdateForm;
import org.example.luvbackend.dto.homeworship.HomeworshipDeleteForm;
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
import org.springframework.web.multipart.MultipartFile;

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
		List<String> keys = buildHomeWorshipKeys(form.getDate().toString(), form.getImages(), 1);
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), keys);
		try {
			String hashedPassword = passwordEncoder.encode(form.getPassword());
			return HomeWorshipResponseDto.from(homeworshipRepository.save(HomeWorship.of(form, imageUrls, hashedPassword)));
		} catch (Exception e) {
			awsS3Service.deleteFiles(imageUrls);
			throw e;
		}
	}

	/**
	 * 단건 가정예배 수정
	 */
	@Transactional
	public HomeWorshipResponseDto updateHomeWorship(String id, HomeWorshipUpdateForm form) {
		HomeWorship homeworship = homeworshipRepository.findByIdOrElseThrow(id);
		verifyPassword(form.getPassword(), homeworship.getPassword());

		List<String> existingUrls = form.getExistingImageUrls() != null ? form.getExistingImageUrls() : List.of();
		String date = form.getDate() != null ? form.getDate().toString() : homeworship.getDate();
		List<String> keys = buildHomeWorshipKeys(date, form.getImages(), existingUrls.size() + 1);
		List<String> uploadedUrls = awsS3Service.uploadFiles(form.getImages(), keys);
		try {
			List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), uploadedUrls);
			homeworship.update(date, form.getTitle(), form.getContent(), form.getUserName(), form.getIsPinned(), mergedImageUrls);
			return HomeWorshipResponseDto.from(homeworshipRepository.save(homeworship));
		} catch (Exception e) {
			awsS3Service.deleteFiles(uploadedUrls);
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

		awsS3Service.deleteFiles(homeworship.getImageUrls());
		homeworshipRepository.delete(homeworship);
	}

	/**
	 * 다건 가정예배 삭제
	 */
	@Transactional
	public void deleteHomeWorships(List<String> ids) {
		List<HomeWorship> homeWorships = homeworshipRepository.findAllById(ids);
		homeWorships.forEach(hw -> awsS3Service.deleteFiles(hw.getImageUrls()));
		homeworshipRepository.deleteAll(homeWorships);
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

	// homeworships/{date}/{uuid}/01.jpg 형태로 key 생성
	private List<String> buildHomeWorshipKeys(String date, List<MultipartFile> files, int startIndex) {
		if (files == null || files.isEmpty()) return List.of();
		String uuid = UUID.randomUUID().toString();
		List<String> keys = new ArrayList<>();
		int index = startIndex;
		for (MultipartFile file : files) {
			keys.add(String.format("homeworships/%s/%s/%02d.%s", date, uuid, index++, getExtension(file)));
		}
		return keys;
	}

	private String getExtension(MultipartFile file) {
		String original = file.getOriginalFilename();
		if (original != null && original.contains(".")) {
			return original.substring(original.lastIndexOf('.') + 1).toLowerCase();
		}
		return "jpg";
	}

	private void verifyPassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new HomeWorshipException(HomeworshipExceptionCode.INVALID_PASSWORD);
		}
	}
}