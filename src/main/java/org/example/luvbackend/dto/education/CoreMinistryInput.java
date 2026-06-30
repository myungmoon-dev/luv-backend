package org.example.luvbackend.dto.education;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoreMinistryInput {
	private String titleKr;
	private String titleEn;
	private String description;
	private String imgClass;
	/** 기존 이미지 URL 유지할 때 사용 (이미지 새로 업로드 안 한 경우) */
	private String imageUrl;
}
