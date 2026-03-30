package org.example.luvbackend.controller.video;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.video.VideoCreateForm;
import org.example.luvbackend.dto.video.VideoResponseDto;
import org.example.luvbackend.dto.video.VideoUpdateForm;
import org.example.luvbackend.service.VideoService;
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
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
	private final VideoService videoService;

	@Operation(summary = "다건 유튜브타입별 영상조회")
	@GetMapping
	public ApiResponse<PageResponse<VideoResponseDto>> getVideos(
		@RequestParam(name = "type", required = false, defaultValue = "") String type,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(videoService.getVideos(type, page, size));
	}

	@Operation(summary = "단건 유튜브영상 생성")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<VideoResponseDto> createVideo(
		@RequestBody @Valid VideoCreateForm form
	) {
		return ApiResponse.created(videoService.createVideo(form));
	}

	@Operation(summary = "단건 유튜브영상 수정")
	@PatchMapping("/{id}")
	public ApiResponse<VideoResponseDto> updateVideo(
		@PathVariable(name = "id") String id,
		@RequestBody @Valid VideoUpdateForm form
	) {
		return ApiResponse.success(videoService.updateVideo(id, form));
	}

	@Operation(summary = "단건 유튜브영상 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteVideo(
		@PathVariable(name = "id") String id
	) {
		videoService.deleteVideo(id);
		return ApiResponse.noContent();
	}
}
