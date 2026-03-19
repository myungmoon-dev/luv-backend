package org.example.luvbackend.dto.bible;

import java.util.List;

import org.example.luvbackend.entity.bible.Bible;
import org.example.luvbackend.entity.bible.BibleLink;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleResponseDto {
	private final String id;
	private final String date;
	private final String title;
	private final String content;
	private final List<BibleLink> links;
	private final Long createdAt;

	@Builder
	private BibleResponseDto(Bible bible) {
		this.id = bible.getId();
		this.date = bible.getDate();
		this.title = bible.getTitle();
		this.content = bible.getContent();
		this.links = bible.getLinks();
		this.createdAt = bible.getCreatedAt();
	}

	public static BibleResponseDto from(Bible bible) {
		return BibleResponseDto.builder()
			.bible(bible)
			.build();
	}
}
