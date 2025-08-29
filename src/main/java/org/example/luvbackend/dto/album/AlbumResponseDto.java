package org.example.luvbackend.dto.album;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.example.luvbackend.entity.album.Album;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumResponseDto {
	private final String id;
	private final String title;
	private final String date;
	private final String albumType;
	private final List<String> imageUrls;
	private final String createdAt;

	@Builder
	public AlbumResponseDto(Album album){
		this.id = album.getId();
		this.title = album.getTitle();
		this.date = album.getDate();
		this.albumType = album.getType();
		this.imageUrls = album.getImageUrls();
		this.createdAt = album.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));;
	}
}
