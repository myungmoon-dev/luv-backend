package org.example.luvbackend.dto.board;

import java.util.List;

import org.example.luvbackend.entity.board.Board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {
	private final String id;
	private final String type;
	private final String title;
	private final String writer;
	private final String content;
	private final List<String> imageUrls;
	private final List<String> fileUrls;
	private final Long createdAt;
	private final Long updatedAt;

	@Builder(access = AccessLevel.PRIVATE)
	private BoardResponseDto(String id, String type, String title, String writer, String content,
		List<String> imageUrls, List<String> fileUrls, Long createdAt, Long updatedAt) {
		this.id = id;
		this.type = type;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.imageUrls = imageUrls;
		this.fileUrls = fileUrls;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static BoardResponseDto from(Board board) {
		return BoardResponseDto.builder()
			.id(board.getId())
			.type(board.getType())
			.title(board.getTitle())
			.writer(board.getWriter())
			.content(board.getContent())
			.imageUrls(board.getImageUrls())
			.fileUrls(board.getFileUrls())
			.createdAt(board.getCreatedAt())
			.updatedAt(board.getUpdatedAt())
			.build();
	}
}
