package org.example.luvbackend.controller.bible;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.bible.BibleCreateForm;
import org.example.luvbackend.dto.bible.BibleResponseDto;
import org.example.luvbackend.service.BibleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bibles")
@RequiredArgsConstructor
public class BibleController {
	private final BibleService bibleService;

	@Operation(summary = "다건 페이징 성경통독 조회")
	@GetMapping
	public ApiResponse<Page<BibleResponseDto>> getBibles(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(bibleService.getBibles(page, size));
	}

	@Operation(summary = "단건 성경통독 조회")
	@GetMapping("/{id}")
	public ApiResponse<BibleResponseDto> getBible(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(bibleService.getBible(id));
	}

	@Operation(summary = "단건 성경통독 생성")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<BibleResponseDto> createBible(
		@RequestBody @Valid BibleCreateForm form
	) {
		return ApiResponse.created(bibleService.createBible(form));
	}

	@Operation(summary = "단건 성경통독 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteBible(
		@PathVariable(name = "id") String id
	) {
		bibleService.deleteBible(id);
		return ApiResponse.noContent();
	}
}
