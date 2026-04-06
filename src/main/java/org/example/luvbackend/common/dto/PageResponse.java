package org.example.luvbackend.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponse<T> {
	private final List<T> content; // 리스트 데이터
	private final long totalElements; // 전체 데이터 개수

	@Builder(access = AccessLevel.PRIVATE)
	private PageResponse (Page<T> page) {
		this.content = page.getContent();
		this.totalElements = page.getTotalElements();
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static <T> PageResponse<T> of(Page<T> page) {
		return PageResponse.<T>builder()
			.page(page)
			.build();
	}
}
