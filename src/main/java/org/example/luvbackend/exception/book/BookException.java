package org.example.luvbackend.exception.book;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BookException extends BaseException {
	private final BookExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public BookException(BookExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
