package org.example.luvbackend.dto;

import org.example.luvbackend.entity.album.Album;

import lombok.Getter;

@Getter
public class AlbumResponseDto {
	private final String title;
	private final String date;
	private final String albumType;
	private final String[] imgUrls;
	// createdAt 넣어야 함

	public AlbumResponseDto(Album album){
		this.title = album.getTitle();
		this.date = album.getDate();
		this.albumType = album.getType();
		this.imgUrls = album.getImgUrls();
	}
}
