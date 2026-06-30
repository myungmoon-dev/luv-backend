package org.example.luvbackend.controller.education;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.education.EducationHomeForm;
import org.example.luvbackend.dto.education.EducationHomeResponseDto;
import org.example.luvbackend.service.EducationHomeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/education-home")
@RequiredArgsConstructor
public class EducationHomeController {
	private final EducationHomeService educationHomeService;

	@Operation(summary = "다음세대 홈 콘텐츠 조회")
	@GetMapping
	public ApiResponse<EducationHomeResponseDto> get() {
		return ApiResponse.success(educationHomeService.get());
	}

	@Operation(summary = "다음세대 홈 콘텐츠 저장/업데이트")
	@PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<EducationHomeResponseDto> upsert(@ModelAttribute EducationHomeForm form) {
		return ApiResponse.success(educationHomeService.upsert(form));
	}
}
