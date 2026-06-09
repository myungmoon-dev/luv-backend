package org.example.luvbackend.exception.homeimage;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HomeImageExceptionCode {
	NOT_FOUND_HOME_IMAGE(false, HttpStatus.NOT_FOUND, "홈 이미지를 찾을 수 없습니다."),
	NO_IMAGE_PROVIDED(false, HttpStatus.BAD_REQUEST, "이미지를 업로드해주세요.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}