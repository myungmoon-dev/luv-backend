package org.example.luvbackend.entity.popup;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "popups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Popup extends BaseEntity {
	@Id
	private String id;

	private String title;
	private Boolean isShow;
	private String imageUrl;

	@Builder
	private Popup(String title, boolean isShow, String imageUrl) {
		this.title = title;
		this.isShow = isShow;
		this.imageUrl = imageUrl;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Popup of(String title, boolean isShow, String imageUrl) {
		return Popup.builder()
			.title(title)
			.isShow(isShow)
			.imageUrl(imageUrl)
			.build();
	}

	public void toggleShow() {
		this.isShow = !this.isShow;
	}
}
