package org.example.luvbackend.dto.live;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LiveUpdateForm {
	@NotBlank(message = "라이브 URL은 필수 입력값입니다.")
	private String url;
}
