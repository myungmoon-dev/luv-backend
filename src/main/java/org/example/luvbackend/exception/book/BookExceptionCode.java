package org.example.luvbackend.exception.book;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookExceptionCode {
	NOT_FOUND_BOOK(false, HttpStatus.NOT_FOUND, "도서를 찾을 수 없습니다."),
	IMAGE_SIZE_OVER_FORM(false, HttpStatus.BAD_REQUEST, "이미지 크기는 10MB를 초과할 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
