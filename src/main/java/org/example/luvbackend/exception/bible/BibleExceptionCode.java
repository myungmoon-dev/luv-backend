package org.example.luvbackend.exception.bible;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BibleExceptionCode {
	NOT_FOUND_BIBLE(false, HttpStatus.NOT_FOUND, "성경통독 자료를 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
