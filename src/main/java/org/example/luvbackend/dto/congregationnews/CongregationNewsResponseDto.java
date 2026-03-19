package org.example.luvbackend.dto.congregationnews;

import org.example.luvbackend.entity.congregationnews.CongregationNews;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CongregationNewsResponseDto {
	private final String id;
	private final String type;
	private final String description;
	private final Long createdAt;
	private final Long updatedAt;

	@Builder(access = AccessLevel.PRIVATE)
	private CongregationNewsResponseDto(CongregationNews news) {
		this.id = news.getId();
		this.type = news.getType();
		this.description = news.getDescription();
		this.createdAt = news.getCreatedAt();
		this.updatedAt = news.getUpdatedAt();
	}

	public static CongregationNewsResponseDto from(CongregationNews news) {
		return CongregationNewsResponseDto.builder()
			.news(news)
			.build();
	}
}
