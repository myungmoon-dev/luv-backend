package org.example.luvbackend.dto.pastorbook;

import org.example.luvbackend.entity.pastorbook.PastorBook;

import lombok.Getter;

@Getter
public class PastorBookResponseDto {
	private final String id;
	private final String title;
	private final String sub;
	private final String publisher;
	private final String year;
	private final String imageUrl;
	private final Long createdAt;

	public PastorBookResponseDto(PastorBook pastorBook) {
		this.id = pastorBook.getId();
		this.title = pastorBook.getTitle();
		this.sub = pastorBook.getSub();
		this.publisher = pastorBook.getPublisher();
		this.year = pastorBook.getYear();
		this.imageUrl = pastorBook.getImageUrl();
		this.createdAt = pastorBook.getCreatedAt();
	}
}
