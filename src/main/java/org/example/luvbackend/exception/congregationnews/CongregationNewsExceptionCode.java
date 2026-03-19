package org.example.luvbackend.exception.congregationnews;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CongregationNewsExceptionCode {
	ILLEGAL_CONGREGATION_NEWS_TYPE(false, HttpStatus.BAD_REQUEST, "존재하지 않는 교회 소식 타입입니다."),
	NOT_FOUND_CONGREGATION_NEWS(false, HttpStatus.NOT_FOUND, "교회 소식을 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
