package org.example.luvbackend.exception.pastorbook;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PastorBookException extends BaseException {
	private final PastorBookExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public PastorBookException(PastorBookExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
