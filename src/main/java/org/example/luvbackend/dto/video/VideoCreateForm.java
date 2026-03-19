package org.example.luvbackend.dto.video;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoCreateForm {
	@NotBlank(message = "영상 URL은 필수 입력값입니다.")
	private String url;

	@NotBlank(message = "영상 제목은 필수 입력값입니다.")
	private String title;

	@NotBlank(message = "날짜는 필수 입력값입니다.")
	private String date;

	@NotBlank(message = "본문은 필수 입력값입니다.")
	private String mainText;

	@NotBlank(message = "설교자는 필수 입력값입니다.")
	private String preacher;

	@NotNull(message = "영상 타입은 필수 입력값입니다.")
	private String type;
}
