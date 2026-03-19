package org.example.luvbackend.dto.bulletin;

import java.util.List;

import org.example.luvbackend.entity.bulletin.Bulletin;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BulletinResponseDto {
	private final String id;
	private final String date;
	private final String title;
	private final List<String> imageUrls;
	private final Long createdAt;

	@Builder
	private BulletinResponseDto(String id, String date, String title, List<String> imageUrls, Long createdAt) {
		this.id = id;
		this.date = date;
		this.title = title;
		this.imageUrls = imageUrls;
		this.createdAt = createdAt;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static BulletinResponseDto from(Bulletin bulletin) {
		return BulletinResponseDto.builder()
			.id(bulletin.getId())
			.date(bulletin.getDate())
			.title(bulletin.getTitle())
			.imageUrls(bulletin.getImageUrls())
			.createdAt(bulletin.getCreatedAt())
			.build();
	}
}
