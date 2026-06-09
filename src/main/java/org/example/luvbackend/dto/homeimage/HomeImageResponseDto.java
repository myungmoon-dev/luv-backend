package org.example.luvbackend.dto.homeimage;

import org.example.luvbackend.entity.homeimage.HomeImage;

import lombok.Getter;

@Getter
public class HomeImageResponseDto {
	private final String id;
	private final String imageUrl;
	private final Long createdAt;

	public HomeImageResponseDto(HomeImage homeImage) {
		this.id = homeImage.getId();
		this.imageUrl = homeImage.getImageUrl();
		this.createdAt = homeImage.getCreatedAt();
	}
}