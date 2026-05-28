package org.example.luvbackend.entity.bulletin;

import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.bulletin.BulletinUploadForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "bulletins")
@Getter
@NoArgsConstructor
public class Bulletin extends BaseEntity {
	@Id
	private String id;

	private String date; // 예배날짜
	private String title; // 제목
	private List<String> imageUrls; // 주보 이미지 리스트

	@Builder
	private Bulletin(String date, String title, List<String> imageUrls) {
		this.date = date;
		this.title = title;
		this.imageUrls = imageUrls;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Bulletin of(BulletinUploadForm form, List<String> imageUrls) {
		return Bulletin.builder()
			.date(form.getDate().toString())
			.title(form.getTitle())
			.imageUrls(imageUrls)
			.build();
	}

	public void updateDate(String date) {
		this.date = date;
	}

	public void updateImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
}
