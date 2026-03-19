package org.example.luvbackend.controller.congregationnews;

import java.util.List;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.congregationnews.CongregationNewsCreateForm;
import org.example.luvbackend.dto.congregationnews.CongregationNewsResponseDto;
import org.example.luvbackend.dto.congregationnews.CongregationNewsUpdateForm;
import org.example.luvbackend.service.CongregationNewsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/congregation-news")
@RequiredArgsConstructor
public class CongregationNewsController {
	private final CongregationNewsService congregationNewsService;

	@Operation(summary = "다건 페이징 교우소식 조회")
	@GetMapping
	public ApiResponse<List<CongregationNewsResponseDto>> getCongregationNewsList(
		@RequestParam(name = "type", required = false) String type
	) {
		return ApiResponse.success(congregationNewsService.getCongregationNewsList(type));
	}

	@Operation(summary = "단건 교우소식 조회")
	@GetMapping("/{id}")
	public ApiResponse<CongregationNewsResponseDto> getCongregationNews(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(congregationNewsService.getCongregationNews(id));
	}

	@Operation(summary = "단건 교우소식 생성")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<CongregationNewsResponseDto> createCongregationNews(
		@RequestBody @Valid CongregationNewsCreateForm form
	) {
		return ApiResponse.created(congregationNewsService.createCongregationNews(form));
	}

	@Operation(summary = "단건 교우소식 수정")
	@PatchMapping("/{id}")
	public ApiResponse<CongregationNewsResponseDto> updateCongregationNews(
		@PathVariable(name = "id") String id,
		@RequestBody @Valid CongregationNewsUpdateForm form
	) {
		return ApiResponse.success(congregationNewsService.updateCongregationNews(id, form));
	}

	@Operation(summary = "단건 교우소식 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteCongregationNews(
		@PathVariable(name = "id") String id
	) {
		congregationNewsService.deleteCongregationNews(id);
		return ApiResponse.noContent();
	}
}
