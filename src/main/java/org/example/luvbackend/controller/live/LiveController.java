package org.example.luvbackend.controller.live;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.live.LiveResponseDto;
import org.example.luvbackend.dto.live.LiveUpdateForm;
import org.example.luvbackend.service.LiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/live")
@RequiredArgsConstructor
public class LiveController {
	private final LiveService liveService;

	@GetMapping
	public ApiResponse<LiveResponseDto> getLive() {
		return ApiResponse.success(liveService.getLive());
	}

	@PatchMapping
	public ApiResponse<LiveResponseDto> updateLive(
		@RequestBody @Valid LiveUpdateForm form
	) {
		return ApiResponse.success(liveService.updateLive(form));
	}
}
