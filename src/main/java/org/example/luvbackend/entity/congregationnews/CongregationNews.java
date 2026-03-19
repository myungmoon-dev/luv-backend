package org.example.luvbackend.entity.congregationnews;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.congregationnews.CongregationNewsCreateForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "congregationNews")
@Getter
@NoArgsConstructor
public class CongregationNews extends BaseEntity {
	@Id
	private String id;

	private CongregationNewsType type;
	private String description;

	public String getType() {
		return type.getValue();
	}

	@Builder
	public CongregationNews(CongregationNewsType type, String description) {
		this.type = type;
		this.description = description;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static CongregationNews of(CongregationNewsCreateForm form) {
		return CongregationNews.builder()
			.type(CongregationNewsType.deserialize(form.getType()))
			.description(form.getDescription())
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(CongregationNewsType type, String description) {
		if (type != null) this.type = type;
		if (description != null) this.description = description;
	}
}
