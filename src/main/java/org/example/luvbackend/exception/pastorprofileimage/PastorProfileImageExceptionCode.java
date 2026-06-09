package org.example.luvbackend.exception.pastorprofileimage;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PastorProfileImageExceptionCode {
	NOT_FOUND_PASTOR_PROFILE_IMAGE(false, HttpStatus.NOT_FOUND, "담임목사 프로필 이미지를 찾을 수 없습니다."),
	NO_IMAGE_PROVIDED(false, HttpStatus.BAD_REQUEST, "변경할 이미지를 선택해주세요.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
