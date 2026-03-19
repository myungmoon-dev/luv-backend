package org.example.luvbackend.exception.live;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class LiveException extends BaseException {
	private final LiveExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public LiveException(LiveExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
