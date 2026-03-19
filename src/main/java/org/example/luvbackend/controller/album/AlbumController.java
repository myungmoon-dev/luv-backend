package org.example.luvbackend.controller.album;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.album.AlbumUploadForm;
import org.example.luvbackend.dto.album.AlbumResponseDto;
import org.example.luvbackend.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

	private final AlbumService albumService;

	@Operation(summary = "단건 앨범 생성")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<AlbumResponseDto> createAlbum(
		@ModelAttribute @Valid AlbumUploadForm requestDto
	) {
		return ApiResponse.created(albumService.createAlbum(requestDto));
	}

	/**
	 * 다건 앨범 조회 메서드
	 * @param type 앨범종류 (main, infants, toddlers, elementary, middle, high, youth, qt, panorama, newFamily, newlyweds, 3040)
	 */
	@Operation(summary = "페이징 다건 타입별 앨범 조회")
	@GetMapping
	public ApiResponse<PageResponse<AlbumResponseDto>> getAlbums(
		@RequestParam(name = "type", required = false, defaultValue = "") String type,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(albumService.getAlbums(type, page, size));
	}

	/**
	 * 단건 앨범 조회 메서드
	 */
	@Operation(summary = "단건 앨범 조회")
	@GetMapping("/{id}")
	public ApiResponse<AlbumResponseDto> getAlbum(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(albumService.getAlbum(id));
	}

	/**
	 * 단건 앨범 삭제 메서드
	 */
	@Operation(summary = "단건 앨범 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteAlbum(
		@PathVariable(name = "id") String id
	) {
		albumService.deleteAlbum(id);
		return ApiResponse.noContent();
	}
}
