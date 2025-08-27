package org.example.luvbackend.exception.album;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AlbumException extends BaseException {
	private final AlbumExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public AlbumException(AlbumExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}

