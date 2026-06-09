package org.example.luvbackend.exception.homeimage;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HomeImageException extends BaseException {
	private final HomeImageExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public HomeImageException(HomeImageExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}