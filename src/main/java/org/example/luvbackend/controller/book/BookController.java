package org.example.luvbackend.controller.book;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.book.BookResponseDto;
import org.example.luvbackend.dto.book.BookUpdateForm;
import org.example.luvbackend.dto.book.BookUploadForm;
import org.example.luvbackend.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@Operation(summary = "다건 페이징 추천도서 조회")
	@GetMapping
	public ApiResponse<Page<BookResponseDto>> getBooks(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(bookService.getBooks(page, size));
	}

	@Operation(summary = "단건 추천도서 조회")
	@GetMapping("/{id}")
	public ApiResponse<BookResponseDto> getBook(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(bookService.getBook(id));
	}

	@Operation(summary = "단건 추천도서 생성")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<BookResponseDto> createBook(
		@ModelAttribute @Valid BookUploadForm form
	) {
		return ApiResponse.created(bookService.createBook(form));
	}

	@Operation(summary = "단건 추천도서 수정")
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<BookResponseDto> updateBook(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid BookUpdateForm form
	) {
		return ApiResponse.success(bookService.updateBook(id, form));
	}

	@Operation(summary = "단건 추천도서 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteBook(
		@PathVariable(name = "id") String id
	) {
		bookService.deleteBook(id);
		return ApiResponse.noContent();
	}
}
