package org.example.luvbackend.dto.album;

import java.util.List;

import org.example.luvbackend.entity.album.Album;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlbumResponseDto {
	private final String id;
	private final String title; // 앨범 제목
	private final String date; // 행사 날짜
	private final String albumType; // 앨범타입
	private final List<String> imageUrls; // 앨범 이미지 리스트
	private final Long createdAt;

	@Builder(access = AccessLevel.PRIVATE)
	private AlbumResponseDto(Album album) {
		this.id = album.getId();
		this.title = album.getTitle();
		this.date = album.getDate();
		this.albumType = album.getType();
		this.imageUrls = album.getImageUrls();
		this.createdAt = album.getCreatedAt();
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static AlbumResponseDto from(Album album){
		return AlbumResponseDto.builder()
			.album(album)
			.build();
	}
}
