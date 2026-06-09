package org.example.luvbackend.exception.pastorbook;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PastorBookExceptionCode {
	NOT_FOUND_PASTOR_BOOK(false, HttpStatus.NOT_FOUND, "저서를 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
