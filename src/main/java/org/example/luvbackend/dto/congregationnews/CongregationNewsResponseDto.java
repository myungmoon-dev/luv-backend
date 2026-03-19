package org.example.luvbackend.dto.congregationnews;

import java.time.format.DateTimeFormatter;

import org.example.luvbackend.entity.congregationnews.CongregationNews;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CongregationNewsResponseDto {
	private final String id;
	private final String type;
	private final String description;
	private final String createdAt;
	private final String updatedAt;

	@Builder(access = AccessLevel.PRIVATE)
	private CongregationNewsResponseDto(CongregationNews news) {
		this.id = news.getId();
		this.type = news.getType();
		this.description = news.getDescription();
		this.createdAt = news.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.updatedAt = news.getUpdatedAt() != null
			? news.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			: null;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static CongregationNewsResponseDto from(CongregationNews news) {
		return CongregationNewsResponseDto.builder()
			.news(news)
			.build();
	}
}
