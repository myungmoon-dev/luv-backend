package org.example.luvbackend.entity.bible;

import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.bible.BibleCreateForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "bibles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bible extends BaseEntity {
	@Id
	private String id;

	private String date; // 성경통독 날짜
	private String title; // 제목
	private String content; // 내용
	private List<BibleLink> links; // 유튜브링크와 플레이리스트 여부

	@Builder
	private Bible(String date, String title, String content, List<BibleLink> links) {
		this.date = date;
		this.title = title;
		this.content = content;
		this.links = links;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Bible from(BibleCreateForm form) {
		return Bible.builder()
			.date(form.getDate().toString())
			.title(form.getTitle())
			.content(form.getContent())
			.links(form.getLinks())
			.build();
	}
}
