package org.example.luvbackend.entity.popup;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "popups")
@Getter
@NoArgsConstructor
public class Popup extends BaseEntity {
	@Id
	private String id;

	private String title;
	private boolean isShow;
	private String imageUrl;

	@Builder
	public Popup(String title, boolean isShow, String imageUrl) {
		this.title = title;
		this.isShow = isShow;
		this.imageUrl = imageUrl;
	}

	public void toggleShow() {
		this.isShow = !this.isShow;
	}
}
