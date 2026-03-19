package org.example.luvbackend.dto.video;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoUpdateForm {
	// PATCH 방식 - 수정할 필드만 전달, null인 필드는 기존 값 유지
	private String url;
	private String title;
	private String date;
	private String mainText;
	private String preacher;
	private String type;
}
