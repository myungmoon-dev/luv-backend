package org.example.luvbackend.controller.missionnews;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.missionnews.MissionNewsCreateForm;
import org.example.luvbackend.dto.missionnews.MissionNewsResponseDto;
import org.example.luvbackend.dto.missionnews.MissionNewsUpdateForm;
import org.example.luvbackend.service.MissionNewsService;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mission-news")
@RequiredArgsConstructor
public class MissionNewsController {
	private final MissionNewsService missionNewsService;

	@GetMapping
	public ApiResponse<PageResponse<MissionNewsResponseDto>> getMissionNewsList(
		@RequestParam(name = "location", required = false) String location,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(missionNewsService.getMissionNewsList(location, page, size));
	}

	@GetMapping("/{id}")
	public ApiResponse<MissionNewsResponseDto> getMissionNews(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(missionNewsService.getMissionNews(id));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<MissionNewsResponseDto> createMissionNews(
		@ModelAttribute @Valid MissionNewsCreateForm form
	) {
		return ApiResponse.created(missionNewsService.createMissionNews(form));
	}

	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<MissionNewsResponseDto> updateMissionNews(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid MissionNewsUpdateForm form
	) {
		return ApiResponse.success(missionNewsService.updateMissionNews(id, form));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteMissionNews(
		@PathVariable(name = "id") String id
	) {
		missionNewsService.deleteMissionNews(id);
		return ApiResponse.noContent();
	}
}
