package org.example.luvbackend.controller.education;

import java.util.List;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.education.EducationForm;
import org.example.luvbackend.dto.education.EducationResponseDto;
import org.example.luvbackend.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/education")
@RequiredArgsConstructor
public class EducationController {
	private final EducationService educationService;

	@Operation(summary = "다음세대 부서 목록 조회")
	@GetMapping
	public ApiResponse<List<EducationResponseDto>> getEducations() {
		return ApiResponse.success(educationService.getEducations());
	}

	@Operation(summary = "다음세대 부서 단건 조회 (type)")
	@GetMapping("/{type}")
	public ApiResponse<EducationResponseDto> getEducationByType(@PathVariable(name = "type") String type) {
		return ApiResponse.success(educationService.getEducationByType(type));
	}

	@Operation(summary = "다음세대 부서 등록")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<EducationResponseDto> createEducation(@ModelAttribute @Valid EducationForm form) {
		return ApiResponse.created(educationService.createEducation(form));
	}

	@Operation(summary = "다음세대 부서 수정")
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<EducationResponseDto> updateEducation(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid EducationForm form
	) {
		return ApiResponse.success(educationService.updateEducation(id, form));
	}

	@Operation(summary = "다음세대 부서 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteEducation(@PathVariable(name = "id") String id) {
		educationService.deleteEducation(id);
		return ApiResponse.noContent();
	}
}
