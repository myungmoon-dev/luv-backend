package org.example.luvbackend.dto.popup;

import java.time.format.DateTimeFormatter;

import org.example.luvbackend.entity.popup.Popup;

import lombok.Getter;

@Getter
public class PopupResponseDto {
	private final String id;
	private final String title;
	private final boolean isShow;
	private final String imageUrl;
	private final String createdAt;

	public PopupResponseDto(Popup popup) {
		this.id = popup.getId();
		this.title = popup.getTitle();
		this.isShow = popup.isShow();
		this.imageUrl = popup.getImageUrl();
		this.createdAt = popup.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
