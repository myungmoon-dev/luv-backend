package org.example.luvbackend.entity.missionnews;

import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.missionnews.MissionNewsCreateForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "missionNewsList")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionNews extends BaseEntity {
	@Id
	private String id;

	private String title;
	private String content;
	private String userName;
	private String date;
	private String location;
	private List<String> imageUrls;

	@Builder
	private MissionNews(String title, String content, String userName, String date,
		String location, List<String> imageUrls) {
		this.title = title;
		this.content = content;
		this.userName = userName;
		this.date = date;
		this.location = location;
		this.imageUrls = imageUrls;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static MissionNews of(MissionNewsCreateForm form, List<String> imageUrls) {
		return MissionNews.builder()
			.title(form.getTitle())
			.content(form.getContent())
			.userName(form.getUserName())
			.date(form.getDate().toString())
			.location(form.getLocation())
			.imageUrls(imageUrls)
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(String title, String content, String userName, String date,
		String location, List<String> imageUrls) {
		if (title != null) this.title = title;
		if (content != null) this.content = content;
		if (userName != null) this.userName = userName;
		if (date != null) this.date = date;
		if (location != null) this.location = location;
		if (imageUrls != null && !imageUrls.isEmpty()) this.imageUrls = imageUrls;
	}
}
