package org.example.luvbackend.dto.missionnews;

import java.util.List;

import org.example.luvbackend.entity.missionnews.MissionNews;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MissionNewsResponseDto {
	private final String id;
	private final String title;
	private final String content;
	private final String userName;
	private final String date;
	private final String location;
	private final List<String> imageUrls;
	private final Long createdAt;
	private final Long updatedAt;

	@Builder(access = AccessLevel.PRIVATE)
	private MissionNewsResponseDto(MissionNews missionNews) {
		this.id = missionNews.getId();
		this.title = missionNews.getTitle();
		this.content = missionNews.getContent();
		this.userName = missionNews.getUserName();
		this.date = missionNews.getDate();
		this.location = missionNews.getLocation();
		this.imageUrls = missionNews.getImageUrls();
		this.createdAt = missionNews.getCreatedAt();
		this.updatedAt = missionNews.getUpdatedAt();
	}

	public static MissionNewsResponseDto from(MissionNews missionNews) {
		return MissionNewsResponseDto.builder()
			.missionNews(missionNews)
			.build();
	}
}
