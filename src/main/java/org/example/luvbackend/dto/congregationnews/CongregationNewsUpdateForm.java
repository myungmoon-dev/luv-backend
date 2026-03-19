package org.example.luvbackend.dto.congregationnews;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CongregationNewsUpdateForm {
	// PATCH 방식 - 수정할 필드만 전달, null인 필드는 기존 값 유지
	private String type;
	private String description;
}
