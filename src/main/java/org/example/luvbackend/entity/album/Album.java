package org.example.luvbackend.entity.album;

import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.album.AlbumUploadForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "albums")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends BaseEntity {
	@Id
	private String id;

	private String title; // 앨범 제목
	private String date; // 앨범 날짜
	private AlbumType type; // DB 에는 AlbumType.value()로 저장되고 있음
	private List<String> imageUrls; // 앨범 이미지 목록

	@Builder
	private Album(String title, String date, AlbumType type, List<String> imageUrls) {
		this.title = title;
		this.date = date;
		this.type = type;
		this.imageUrls = imageUrls;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Album of(AlbumUploadForm form, List<String> imageUrls) {
		return Album.builder()
			.title(form.getTitle())
			.date(form.getDate().toString())
			.type(AlbumType.deserialize(form.getType()))
			.imageUrls(imageUrls)
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(String title, String date, AlbumType type, List<String> imageUrls) {
		if (title != null) this.title = title;
		if (date != null) this.date = date;
		if (type != null) this.type = type;
		if (imageUrls != null && !imageUrls.isEmpty()) this.imageUrls = imageUrls;
	}

	/**
	 * 앨범의 카테고리를 String 형식으로 반환하는 메서드
	 */
	public String getType() {
		return type.getValue();
	}
}
