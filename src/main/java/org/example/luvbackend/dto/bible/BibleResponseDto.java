package org.example.luvbackend.dto.bible;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.example.luvbackend.entity.bible.Bible;
import org.example.luvbackend.entity.bible.BibleLink;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleResponseDto {
	private final String id;
	private final String date; // 성경통독 날짜
	private final String title; // 제목
	private final String content; // 내용
	private final List<BibleLink> links; // 유튜브 링크와 플레이리스트 여부
	private final String createdAt; // 생성 날짜

	@Builder
	private BibleResponseDto(Bible bible) {
		this.id = bible.getId();
		this.date = bible.getDate();
		this.title = bible.getTitle();
		this.content = bible.getContent();
		this.links = bible.getLinks();
		this.createdAt = bible.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static BibleResponseDto from(Bible bible) {
		return BibleResponseDto.builder()
			.bible(bible)
			.build();
	}
}
