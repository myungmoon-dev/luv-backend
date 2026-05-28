package org.example.luvbackend.controller.bulletin;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.bulletin.BulletinResponseDto;
import org.example.luvbackend.dto.bulletin.BulletinUpdateForm;
import org.example.luvbackend.dto.bulletin.BulletinUploadForm;
import org.example.luvbackend.service.BulletinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/api/bulletins")
@RequiredArgsConstructor
public class BulletinController {
	private final BulletinService bulletinService;

	@Operation(summary = "다건 페이징 주보 조회")
	@GetMapping
	public ApiResponse<PageResponse<BulletinResponseDto>> getBulletins(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size,
		@RequestParam(name = "year", required = false) String year,
		@RequestParam(name = "month", required = false) String month
	) {
		return ApiResponse.success(bulletinService.getBulletins(page, size, year, month));
	}

	@Operation(summary = "주보 존재 연/월 조회")
	@GetMapping("/dates")
	public ApiResponse<Map<String, List<String>>> getAvailableDates() {
		return ApiResponse.success(bulletinService.getAvailableDates());
	}

	@Operation(summary = "단건 주보 조회")
	@GetMapping("/{id}")
	public ApiResponse<BulletinResponseDto> getBulletin(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(bulletinService.getBulletin(id));
	}

	@Operation(summary = "단건 주보 생성")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<BulletinResponseDto> createBulletin(
		@ModelAttribute @Valid BulletinUploadForm form
	) {
		return ApiResponse.created(bulletinService.createBulletin(form));
	}

	@Operation(summary = "단건 주보 수정")
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<BulletinResponseDto> updateBulletin(
		@PathVariable(name = "id") String id,
		@ModelAttribute BulletinUpdateForm form
	) {
		return ApiResponse.success(bulletinService.updateBulletin(id, form));
	}

	@Operation(summary = "단건 주보 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteBulletin(
		@PathVariable(name = "id") String id
	) {
		bulletinService.deleteBulletin(id);
		return ApiResponse.noContent();
	}

	@Operation(summary = "다건 주보 삭제")
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteBulletins(
		@RequestBody List<String> ids
	) {
		bulletinService.deleteBulletins(ids);
		return ApiResponse.noContent();
	}
}
