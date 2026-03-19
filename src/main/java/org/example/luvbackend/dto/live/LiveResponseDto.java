package org.example.luvbackend.dto.live;

import java.time.format.DateTimeFormatter;

import org.example.luvbackend.entity.live.Live;

import lombok.Getter;

@Getter
public class LiveResponseDto {
	private final String url;
	private final String updatedAt;

	public LiveResponseDto(Live live) {
		this.url = live.getUrl();
		this.updatedAt = live.getUpdatedAt() != null
			? live.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			: null;
	}
}
