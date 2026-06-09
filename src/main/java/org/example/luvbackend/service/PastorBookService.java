package org.example.luvbackend.service;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.pastorbook.PastorBookForm;
import org.example.luvbackend.dto.pastorbook.PastorBookResponseDto;
import org.example.luvbackend.entity.pastorbook.PastorBook;
import org.example.luvbackend.repository.PastorBookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PastorBookService {
	private final PastorBookRepository pastorBookRepository;
	private final AwsS3Service awsS3Service;

	@Transactional(readOnly = true)
	public PageResponse<PastorBookResponseDto> getPastorBooks(int page, int size) {
		return PageResponse.of(
			pastorBookRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
				.map(PastorBookResponseDto::new)
		);
	}

	@Transactional
	public PastorBookResponseDto createPastorBook(PastorBookForm form) {
		MultipartFile image = form.getImage();
		if (image == null || image.isEmpty()) {
			throw new IllegalArgumentException("이미지는 필수입니다.");
		}
		String imageUrl = awsS3Service.uploadFile(image, buildKey(image, form.getTitle()));
		return new PastorBookResponseDto(pastorBookRepository.save(PastorBook.from(form, imageUrl)));
	}

	@Transactional
	public PastorBookResponseDto updatePastorBook(String id, PastorBookForm form) {
		PastorBook pastorBook = pastorBookRepository.findByIdOrElseThrow(id);

		String imageUrl = null;
		MultipartFile image = form.getImage();
		if (image != null && !image.isEmpty()) {
			awsS3Service.deleteFile(pastorBook.getImageUrl());
			imageUrl = awsS3Service.uploadFile(image, buildKey(image, form.getTitle()));
		}

		pastorBook.update(form, imageUrl);
		return new PastorBookResponseDto(pastorBookRepository.save(pastorBook));
	}

	// leadership/pastor/pastorBooks/{title}.{ext}
	private String buildKey(MultipartFile file, String title) {
		String ext = getExtension(file);
		return String.format("leadership/pastor/pastorBooks/%s.%s", title, ext);
	}

	private String getExtension(MultipartFile file) {
		String original = file.getOriginalFilename();
		if (original != null && original.contains(".")) {
			return original.substring(original.lastIndexOf('.') + 1).toLowerCase();
		}
		return "png";
	}

	@Transactional
	public void deletePastorBook(String id) {
		PastorBook pastorBook = pastorBookRepository.findByIdOrElseThrow(id);
		awsS3Service.deleteFile(pastorBook.getImageUrl());
		pastorBookRepository.delete(pastorBook);
	}
}