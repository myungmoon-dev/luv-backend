package org.example.luvbackend.dto.live;

import org.example.luvbackend.entity.live.Live;

import lombok.Getter;

@Getter
public class LiveResponseDto {
	private final String url;
	private final Long updatedAt;

	public LiveResponseDto(Live live) {
		this.url = live.getUrl();
		this.updatedAt = live.getUpdatedAt();
	}
}
