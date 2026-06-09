package org.example.luvbackend.exception.pastorprofileimage;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PastorProfileImageException extends BaseException {
	private final PastorProfileImageExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public PastorProfileImageException(PastorProfileImageExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
