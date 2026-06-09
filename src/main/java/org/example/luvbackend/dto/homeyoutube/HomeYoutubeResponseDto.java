package org.example.luvbackend.dto.homeyoutube;

import org.example.luvbackend.entity.homeyoutube.HomeYoutube;

import lombok.Getter;

@Getter
public class HomeYoutubeResponseDto {
	private final String youtubeUrl;

	public HomeYoutubeResponseDto(HomeYoutube homeYoutube) {
		this.youtubeUrl = homeYoutube.getYoutubeUrl();
	}
}
