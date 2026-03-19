package org.example.luvbackend.controller.video;

import java.util.List;

import org.example.luvbackend.common.dto.ApiResponse;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
	private final VideoService videoService;

	@GetMapping
	public ApiResponse<List<VideoResponseDto>> getVideos(
		@RequestParam(name = "type", required = false) String type
	) {
		return ApiResponse.success(videoService.getVideos(type));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<VideoResponseDto> createVideo(
		@RequestBody @Valid VideoCreateForm form
	) {
		return ApiResponse.created(videoService.createVideo(form));
	}

	@PatchMapping("/{id}")
	public ApiResponse<VideoResponseDto> updateVideo(
		@PathVariable(name = "id") String id,
		@RequestBody @Valid VideoUpdateForm form
	) {
		return ApiResponse.success(videoService.updateVideo(id, form));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteVideo(
		@PathVariable(name = "id") String id
	) {
		videoService.deleteVideo(id);
		return ApiResponse.noContent();
	}
}
