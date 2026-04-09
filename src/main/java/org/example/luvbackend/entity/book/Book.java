package org.example.luvbackend.entity.book;

import java.time.YearMonth;
import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.book.BookUploadForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "books")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Book extends BaseEntity {
	@Id
	private String id;

	private String title; // 제목
	private String content; // 내용
	private String writer; // 작성자
	private String date; // 추천 날짜
	private List<String> imageUrls; // 도서 이미지 리스트

	@Builder
	public Book(String title, String content, String writer, String date, List<String> imageUrls) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.date = date;
		this.imageUrls = (imageUrls != null) ? imageUrls : List.of(); // null 방지
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Book of(BookUploadForm form, List<String> imageUrls) {
		return Book.builder()
			.title(form.getTitle())
			.content(form.getContent())
			.writer(form.getWriter())
			.date(form.getDate().toString())
			.imageUrls(imageUrls)
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(String title, String content, String writer, YearMonth date, List<String> imageUrls) {
		if (title != null) this.title = title;
		if (content != null) this.content = content;
		if (writer != null) this.writer = writer;
		if (date != null) this.date = date.toString();
		if (!imageUrls.isEmpty()) this.imageUrls = imageUrls;
	}
}
