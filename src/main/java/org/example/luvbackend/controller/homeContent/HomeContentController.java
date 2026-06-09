package org.example.luvbackend.controller.homeContent;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.homeimage.HomeImageResponseDto;
import org.example.luvbackend.dto.homeyoutube.HomeYoutubeForm;
import org.example.luvbackend.dto.homeyoutube.HomeYoutubeResponseDto;
import org.example.luvbackend.service.HomeImageService;
import org.example.luvbackend.service.HomeYoutubeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/home-content")
@RequiredArgsConstructor
public class HomeContentController {
	private final HomeYoutubeService homeYoutubeService;
	private final HomeImageService homeImageService;

	@Operation(summary = "홈 유튜브 링크 조회")
	@GetMapping("/youtube")
	public ApiResponse<HomeYoutubeResponseDto> getHomeYoutube() {
		return ApiResponse.success(homeYoutubeService.getHomeYoutube());
	}

	@Operation(summary = "홈 유튜브 링크 수정")
	@PatchMapping("/youtube")
	public ApiResponse<HomeYoutubeResponseDto> updateHomeYoutube(
		@RequestBody @Valid HomeYoutubeForm form
	) {
		return ApiResponse.success(homeYoutubeService.updateHomeYoutube(form));
	}

	@Operation(summary = "홈 이미지 목록 조회")
	@GetMapping("/images")
	public ApiResponse<PageResponse<HomeImageResponseDto>> getHomeImages(
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(homeImageService.getHomeImages(page, size));
	}

	@Operation(summary = "홈 이미지 추가")
	@PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<HomeImageResponseDto> createHomeImage(
		@RequestParam("image") MultipartFile image
	) {
		return ApiResponse.created(homeImageService.createHomeImage(image));
	}

	@Operation(summary = "홈 이미지 삭제")
	@DeleteMapping("/images/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteHomeImage(
		@PathVariable(name = "id") String id
	) {
		homeImageService.deleteHomeImage(id);
		return ApiResponse.noContent();
	}
}