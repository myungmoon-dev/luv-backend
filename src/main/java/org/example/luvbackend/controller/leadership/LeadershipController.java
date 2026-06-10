package org.example.luvbackend.controller.leadership;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.leadership.LeadershipForm;
import org.example.luvbackend.dto.leadership.LeadershipResponseDto;
import org.example.luvbackend.service.LeadershipService;
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
@RequestMapping("/api/leadership")
@RequiredArgsConstructor
public class LeadershipController {
	private final LeadershipService leadershipService;

	@Operation(summary = "섬기는 분들 목록 조회")
	@GetMapping
	public ApiResponse<PageResponse<LeadershipResponseDto>> getLeaderships(
		@RequestParam(name = "tabType", required = false) String tabType,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "30") int size
	) {
		return ApiResponse.success(leadershipService.getLeaderships(tabType, page, size));
	}

	@Operation(summary = "섬기는 분 등록")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<LeadershipResponseDto> createLeadership(
		@ModelAttribute @Valid LeadershipForm form
	) {
		return ApiResponse.created(leadershipService.createLeadership(form));
	}

	@Operation(summary = "섬기는 분 수정")
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<LeadershipResponseDto> updateLeadership(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid LeadershipForm form
	) {
		return ApiResponse.success(leadershipService.updateLeadership(id, form));
	}

	@Operation(summary = "섬기는 분 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteLeadership(
		@PathVariable(name = "id") String id
	) {
		leadershipService.deleteLeadership(id);
		return ApiResponse.noContent();
	}
}