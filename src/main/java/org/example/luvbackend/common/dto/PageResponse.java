package org.example.luvbackend.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponse<T> {
	private final List<T> content; // 리스트 데이터
	private final int page; // 요청한 페이지
	private final int size; // 페이지당 가져올 데이터 개수
	private final long totalElements; // 전체 데이터 개수
	private final int totalPages; // 총 페이지 수
	private final boolean isLast; // 마지막 페이지 여부
	private final boolean isFirst; // 첫번째 페이지 여부

	@Builder(access = AccessLevel.PRIVATE)
	private PageResponse (Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber();
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.isLast = page.isLast();
		this.isFirst = page.isFirst();
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
