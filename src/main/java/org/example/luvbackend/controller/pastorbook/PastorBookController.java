package org.example.luvbackend.controller.pastorbook;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.pastorbook.PastorBookForm;
import org.example.luvbackend.dto.pastorbook.PastorBookResponseDto;
import org.example.luvbackend.service.PastorBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pastor/books")
@RequiredArgsConstructor
public class PastorBookController {
	private final PastorBookService pastorBookService;

	@Operation(summary = "담임목사 저서 목록 조회")
	@GetMapping
	public ApiResponse<PageResponse<PastorBookResponseDto>> getPastorBooks(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(pastorBookService.getPastorBooks(page, size));
	}

	@Operation(summary = "담임목사 저서 생성")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<PastorBookResponseDto> createPastorBook(
		@ModelAttribute @Valid PastorBookForm form
	) {
		return ApiResponse.created(pastorBookService.createPastorBook(form));
	}

	@Operation(summary = "담임목사 저서 수정")
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<PastorBookResponseDto> updatePastorBook(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid PastorBookForm form
	) {
		return ApiResponse.success(pastorBookService.updatePastorBook(id, form));
	}

	@Operation(summary = "담임목사 저서 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deletePastorBook(
		@PathVariable(name = "id") String id
	) {
		pastorBookService.deletePastorBook(id);
		return ApiResponse.noContent();
	}
}