package org.example.luvbackend.exception.live;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiveExceptionCode {
	NOT_FOUND_LIVE(false, HttpStatus.NOT_FOUND, "라이브 정보를 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
