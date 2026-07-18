package org.example.luvbackend.controller.board;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.board.BoardResponseDto;
import org.example.luvbackend.dto.board.BoardUpdateForm;
import org.example.luvbackend.dto.board.BoardUploadForm;
import org.example.luvbackend.service.BoardService;
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

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@Operation(summary = "다건 페이징 게시글 조회 (type 필터 optional)")
	@GetMapping
	public ApiResponse<PageResponse<BoardResponseDto>> getBoards(
		@RequestParam(name = "type", required = false) String type,
		@RequestParam(name = "page", defaultValue = "0") int page,
		@RequestParam(name = "size", defaultValue = "10") int size
	) {
		return ApiResponse.success(boardService.getBoards(type, page, size));
	}

	@Operation(summary = "단건 게시글 조회")
	@GetMapping("/{id}")
	public ApiResponse<BoardResponseDto> getBoard(
		@PathVariable(name = "id") String id
	) {
		return ApiResponse.success(boardService.getBoard(id));
	}

	@Operation(summary = "단건 게시글 생성")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<BoardResponseDto> createBoard(
		@ModelAttribute @Valid BoardUploadForm form
	) {
		return ApiResponse.created(boardService.createBoard(form));
	}

	@Operation(summary = "단건 게시글 수정")
	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<BoardResponseDto> updateBoard(
		@PathVariable(name = "id") String id,
		@ModelAttribute @Valid BoardUpdateForm form
	) {
		return ApiResponse.success(boardService.updateBoard(id, form));
	}

	@Operation(summary = "단건 게시글 삭제")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse<Void> deleteBoard(
		@PathVariable(name = "id") String id
	) {
		boardService.deleteBoard(id);
		return ApiResponse.noContent();
	}
}
