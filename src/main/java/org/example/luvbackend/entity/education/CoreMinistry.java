package org.example.luvbackend.entity.education;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoreMinistry {
	private String titleKr;
	private String titleEn;
	private String description;
	private String imageUrl;
	private String imgClass;

	@Builder
	public CoreMinistry(String titleKr, String titleEn, String description, String imageUrl, String imgClass) {
		this.titleKr = titleKr;
		this.titleEn = titleEn;
		this.description = description;
		this.imageUrl = imageUrl;
		this.imgClass = imgClass;
	}
}
