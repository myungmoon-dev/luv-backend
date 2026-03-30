package org.example.luvbackend.controller.homeworship;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.homeworship.CommentCreateForm;
import org.example.luvbackend.dto.homeworship.CommentDeleteForm;
import org.example.luvbackend.dto.homeworship.HomeWorshipCreateForm;
import org.example.luvbackend.dto.homeworship.HomeworshipDeleteForm;
import org.example.luvbackend.dto.homeworship.HomeWorshipResponseDto;
import org.example.luvbackend.dto.homeworship.HomeWorshipUpdateForm;
import org.example.luvbackend.service.HomeWorshipService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/homeworships")
@RequiredArgsConstructor
public class HomeWorshipController {
	private final HomeWorshipService homeworshipService;

	@Operation(summary = "다건 페이징 가정예배 조회")
	@GetMapping
	public ApiResponse<Page<HomeWorshipResponseDto>> getHomeWorships(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(homeworshipService.getHomeWorships(page, size));
	}

	@Operation(summary = "단건 가정예배 조회")
	@GetMapping("/{id}")
	public ApiResponse<HomeWorshipResponseDto> getHomeWorship(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(homeworshipService.getHomeWorship(id));
	}

	@Operation(summary = "단건 가정예배 생성")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<HomeWorshipResponseDto> createHomeWorship(
		@ModelAttribute @Valid HomeWorshipCreateForm form
	) {
		return ApiResponse.created(homeworshipService.createHomeWorship(form));
	}

	@Operation(summary = "단건 가정예배 수정")
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<HomeWorshipResponseDto> updateHomeWorship(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid HomeWorshipUpdateForm form
	) {
		return ApiResponse.success(homeworshipService.updateHomeWorship(id, form));
	}

	@Operation(summary = "단건 가정예배 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteHomeWorship(
		@PathVariable(name = "id") String id,
		@RequestBody @Valid HomeworshipDeleteForm form
	) {
		homeworshipService.deleteHomeWorship(id, form);
		return ApiResponse.noContent();
	}

	@Operation(summary = "단건 가정예배 댓글 생성")
	@PostMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<HomeWorshipResponseDto> addComment(
		@PathVariable(name = "id") String id,
		@RequestBody @Valid CommentCreateForm form
	) {
		return ApiResponse.created(homeworshipService.addComment(id, form));
	}

	@Operation(summary = "단건 가정예배 댓글 삭제")
	@DeleteMapping("/{id}/comments/{commentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteComment(
		@PathVariable(name = "id") String id,
		@PathVariable(name = "commentId") String commentId,
		@RequestBody @Valid CommentDeleteForm form
	) {
		homeworshipService.deleteComment(id, commentId, form);
		return ApiResponse.noContent();
	}
}
