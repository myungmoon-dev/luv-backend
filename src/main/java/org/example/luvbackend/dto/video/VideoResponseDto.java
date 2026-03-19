package org.example.luvbackend.dto.video;

import java.time.format.DateTimeFormatter;

import org.example.luvbackend.entity.video.Video;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VideoResponseDto {
	private final String id;
	private final String url;
	private final String title;
	private final String date;
	private final String mainText;
	private final String preacher;
	private final String type;
	private final String createdAt;

	@Builder(access = AccessLevel.PRIVATE)
	private VideoResponseDto(Video video) {
		this.id = video.getId();
		this.url = video.getUrl();
		this.title = video.getTitle();
		this.date = video.getDate();
		this.mainText = video.getMainText();
		this.preacher = video.getPreacher();
		this.type = video.getType();
		this.createdAt = video.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static VideoResponseDto from(Video video) {
		return VideoResponseDto.builder()
			.video(video)
			.build();
	}
}
