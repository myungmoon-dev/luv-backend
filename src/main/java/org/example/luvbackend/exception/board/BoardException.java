package org.example.luvbackend.exception.board;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BoardException extends BaseException {
	private final BoardExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public BoardException(BoardExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
