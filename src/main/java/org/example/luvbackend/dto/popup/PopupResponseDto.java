package org.example.luvbackend.dto.popup;

import org.example.luvbackend.entity.popup.Popup;

import lombok.Getter;

@Getter
public class PopupResponseDto {
	private final String id;
	private final String title;
	private final boolean isShow;
	private final String imageUrl;
	private final Long createdAt;

	public PopupResponseDto(Popup popup) {
		this.id = popup.getId();
		this.title = popup.getTitle();
		this.isShow = popup.getIsShow();
		this.imageUrl = popup.getImageUrl();
		this.createdAt = popup.getCreatedAt();
	}
}
