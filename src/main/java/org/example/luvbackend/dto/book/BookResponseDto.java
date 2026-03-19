package org.example.luvbackend.dto.book;

import java.util.List;

import org.example.luvbackend.entity.book.Book;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookResponseDto {
	private final String id;
	private final String title;
	private final String content;
	private final String writer;
	private final String date;
	private final List<String> imageUrls;
	private final String createdAt;

	@Builder
	private BookResponseDto(String id, String title, String content, String writer,
		String date, List<String> imageUrls, String createdAt) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.date = date;
		this.imageUrls = imageUrls;
		this.createdAt = createdAt;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static BookResponseDto from(Book book) {
		return BookResponseDto.builder()
			.id(book.getId())
			.title(book.getTitle())
			.content(book.getContent())
			.writer(book.getWriter())
			.date(book.getDate())
			.imageUrls(book.getImageUrls())
			.createdAt(book.getFormattedCreatedAt())
			.build();
	}
}
