package org.example.luvbackend.dto.congregationnews;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CongregationNewsCreateForm {
	@NotNull(message = "교회 소식 타입은 필수 입력값입니다.")
	private String type;

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String description;
}
