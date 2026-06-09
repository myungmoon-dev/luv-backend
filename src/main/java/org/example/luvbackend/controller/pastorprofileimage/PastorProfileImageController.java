package org.example.luvbackend.controller.pastorprofileimage;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.pastorprofileimage.PastorProfileImageForm;
import org.example.luvbackend.dto.pastorprofileimage.PastorProfileImageResponseDto;
import org.example.luvbackend.service.PastorProfileImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pastor/profile")
@RequiredArgsConstructor
public class PastorProfileImageController {
	private final PastorProfileImageService pastorProfileImageService;

	@Operation(summary = "담임목사 프로필 이미지 조회")
	@GetMapping
	public ApiResponse<PastorProfileImageResponseDto> getPastorProfileImage() {
		return ApiResponse.success(pastorProfileImageService.getPastorProfileImage());
	}

	@Operation(summary = "담임목사 프로필 이미지 수정")
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<PastorProfileImageResponseDto> updatePastorProfileImage(
		@ModelAttribute PastorProfileImageForm form
	) {
		return ApiResponse.success(pastorProfileImageService.updatePastorProfileImage(form));
	}
}