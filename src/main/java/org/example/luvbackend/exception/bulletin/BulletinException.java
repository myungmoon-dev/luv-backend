package org.example.luvbackend.exception.bulletin;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BulletinException extends BaseException {
	private final BulletinExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public BulletinException(BulletinExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
