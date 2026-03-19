package org.example.luvbackend.exception.homeworship;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HomeworshipExceptionCode {
	NOT_FOUND_HOMEWORSHIP(false, HttpStatus.NOT_FOUND, "가정예배 글을 찾을 수 없습니다."),
	NOT_FOUND_COMMENT(false, HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
	INVALID_PASSWORD(false, HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
	IMAGE_SIZE_OVER_FORM(false, HttpStatus.BAD_REQUEST, "이미지 크기는 10MB를 초과할 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
