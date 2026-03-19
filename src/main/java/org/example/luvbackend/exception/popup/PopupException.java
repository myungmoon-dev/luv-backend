package org.example.luvbackend.exception.popup;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PopupException extends BaseException {
	private final PopupExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public PopupException(PopupExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
