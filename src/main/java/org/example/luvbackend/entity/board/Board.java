package org.example.luvbackend.entity.board;

import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.board.BoardUploadForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "boards")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Board extends BaseEntity {
	@Id
	private String id;

	private String type; // 게시글 타입
	private String title; // 제목
	private String writer; // 작성자
	private String content; // 내용
	private List<String> imageUrls; // 게시글 이미지 리스트
	private List<String> fileUrls; // 게시글 첨부파일 리스트

	@Builder
	public Board(String type, String title, String writer, String content,
		List<String> imageUrls, List<String> fileUrls) {
		this.type = type;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.imageUrls = (imageUrls != null) ? imageUrls : List.of(); // null 방지
		this.fileUrls = (fileUrls != null) ? fileUrls : List.of(); // null 방지
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Board of(BoardUploadForm form, List<String> imageUrls, List<String> fileUrls) {
		return Board.builder()
			.type(form.getType())
			.title(form.getTitle())
			.writer(form.getWriter())
			.content(form.getContent())
			.imageUrls(imageUrls)
			.fileUrls(fileUrls)
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(String type, String title, String writer, String content,
		List<String> imageUrls, List<String> fileUrls) {
		if (type != null) this.type = type;
		if (title != null) this.title = title;
		if (writer != null) this.writer = writer;
		if (content != null) this.content = content;
		if (!imageUrls.isEmpty()) this.imageUrls = imageUrls;
		if (!fileUrls.isEmpty()) this.fileUrls = fileUrls;
	}
}
