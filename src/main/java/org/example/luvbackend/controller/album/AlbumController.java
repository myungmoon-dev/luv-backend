package org.example.luvbackend.controller.album;

import java.util.List;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.album.AlbumUploadForm;
import org.example.luvbackend.dto.album.AlbumResponseDto;
import org.example.luvbackend.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

	private final AlbumService albumService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<AlbumResponseDto> createAlbum(
		@ModelAttribute @Valid AlbumUploadForm requestDto
	) {
		return new ApiResponse<>(
			201,
			albumService.createAlbum(requestDto));
	}

	@GetMapping
	public ApiResponse<List<AlbumResponseDto>> getAlbums() {
		return new ApiResponse<>(
			200,
			albumService.getAlbums());
	}

	@GetMapping("/{id}")
	public ApiResponse<AlbumResponseDto> getAlbum(
		@PathVariable(name = "id") String id
	) {
		return new ApiResponse<>(
			200,
			albumService.getAlbum(id));
	}
}
