package org.example.luvbackend.dto.homeyoutube;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HomeYoutubeForm {
	@NotBlank(message = "유튜브 링크를 입력해주세요.")
	private String youtubeUrl;
}