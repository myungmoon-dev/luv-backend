package org.example.luvbackend.exception.leadership;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LeadershipExceptionCode {
	NOT_FOUND_LEADERSHIP(false, HttpStatus.NOT_FOUND, "섬기는 분을 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}