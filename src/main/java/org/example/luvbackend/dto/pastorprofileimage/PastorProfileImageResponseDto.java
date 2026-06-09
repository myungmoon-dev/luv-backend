package org.example.luvbackend.dto.pastorprofileimage;

import org.example.luvbackend.entity.pastorprofileimage.PastorProfileImage;

import lombok.Getter;

@Getter
public class PastorProfileImageResponseDto {
	private final String topImageUrl;
	private final String bottomImageUrl;

	public PastorProfileImageResponseDto(PastorProfileImage pastorProfileImage) {
		this.topImageUrl = pastorProfileImage.getTopImageUrl();
		this.bottomImageUrl = pastorProfileImage.getBottomImageUrl();
	}
}
