package org.example.luvbackend.exception.homeyoutube;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HomeYoutubeExceptionCode {
	NOT_FOUND_HOME_YOUTUBE(false, HttpStatus.NOT_FOUND, "홈 유튜브 링크를 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}