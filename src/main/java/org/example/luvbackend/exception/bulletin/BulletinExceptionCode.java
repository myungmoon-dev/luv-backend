package org.example.luvbackend.exception.bulletin;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BulletinExceptionCode {
	NOT_FOUND_BULLETIN(false, HttpStatus.NOT_FOUND, "주보를 찾을 수 없습니다."),
	NO_IMAGE_BULLETIN_FORM(false, HttpStatus.BAD_REQUEST, "이미지는 최소 1개 이상 업로드해야 합니다."),
	IMAGE_SIZE_OVER_BULLETIN_FORM(false, HttpStatus.BAD_REQUEST, "이미지 크기는 10MB를 초과할 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
