package org.example.luvbackend.controller.popup;

import java.util.List;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.dto.popup.PopupResponseDto;
import org.example.luvbackend.dto.popup.PopupUploadForm;
import org.example.luvbackend.service.PopupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/popups")
@RequiredArgsConstructor
public class PopupController {
	private final PopupService popupService;

	@GetMapping
	public ApiResponse<List<PopupResponseDto>> getPopups(
		@RequestParam(name = "onlyShow", required = false) Boolean onlyShow
	) {
		return ApiResponse.success(popupService.getPopups(onlyShow));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<PopupResponseDto> createPopup(
		@ModelAttribute @Valid PopupUploadForm form
	) {
		return ApiResponse.created(popupService.createPopup(form));
	}

	@PutMapping("/{id}/show")
	public ApiResponse<PopupResponseDto> toggleShow(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(popupService.toggleShow(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deletePopup(
		@PathVariable(name = "id") String id
	) {
		popupService.deletePopup(id);
		return ApiResponse.noContent();
	}
}
