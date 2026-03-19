package org.example.luvbackend.exception.homeworship;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HomeWorshipException extends BaseException {
	private final HomeworshipExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public HomeWorshipException(HomeworshipExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
