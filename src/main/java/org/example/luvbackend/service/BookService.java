package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.book.BookResponseDto;
import org.example.luvbackend.dto.book.BookUpdateForm;
import org.example.luvbackend.dto.book.BookUploadForm;
import org.example.luvbackend.entity.book.Book;
import org.example.luvbackend.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	private final AwsS3Service awsS3Service;

	/**
	 * 다건 페이징 추천도서 조회
	 */
	@Transactional(readOnly = true)
	public Page<BookResponseDto> getBooks(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return bookRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(BookResponseDto::from);
	}

	/**
	 * 단건 추천도서 조회
	 */
	@Transactional(readOnly = true)
	public BookResponseDto getBook(String id) {
		return BookResponseDto.from(bookRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 추천도서 생성
	 */
	@Transactional
	public BookResponseDto createBook(BookUploadForm form) {
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.BOOKS);
		return BookResponseDto.from(bookRepository.save(Book.of(form, imageUrls)));
	}

	/**
	 * 단건 추천도서 수정
	 */
	@Transactional
	public BookResponseDto updateBook(String id, BookUpdateForm form) {
		Book book = bookRepository.findByIdOrElseThrow(id);

		List<String> newUploadedUrls = awsS3Service.uploadFiles(form.getNewImages(), S3Directory.BOOKS); // 새로운 이미지 업로드
		List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), newUploadedUrls); // 기존이미지 + 새로운이미지 리스트

		book.update(form.getTitle(), form.getContent(), form.getWriter(), form.getDate(), mergedImageUrls);
		return BookResponseDto.from(bookRepository.save(book));
	}

	/**
	 * 단건 추천도서 삭제
	 */
	@Transactional
	public void deleteBook(String id) {
		Book fromDB = bookRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls()); // 이미지 삭제
		bookRepository.delete(fromDB); // DB 삭제
	}

}
