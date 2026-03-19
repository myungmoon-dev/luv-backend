package org.example.luvbackend.exception.bible;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BibleException extends BaseException {
	private final BibleExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public BibleException(BibleExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
