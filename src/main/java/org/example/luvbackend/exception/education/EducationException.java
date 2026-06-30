package org.example.luvbackend.exception.education;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class EducationException extends BaseException {
	private final EducationExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public EducationException(EducationExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
