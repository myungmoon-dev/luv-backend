package org.example.luvbackend.exception.video;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VideoExceptionCode {
	ILLEGAL_VIDEO_TYPE(false, HttpStatus.BAD_REQUEST, "존재하지 않는 영상 타입입니다."),
	NOT_FOUND_VIDEO(false, HttpStatus.NOT_FOUND, "영상을 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
