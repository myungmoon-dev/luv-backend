package org.example.luvbackend.exception.homeyoutube;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HomeYoutubeException extends BaseException {
	private final HomeYoutubeExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public HomeYoutubeException(HomeYoutubeExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
