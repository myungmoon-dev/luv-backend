package org.example.luvbackend.exception.congregationnews;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CongregationNewsException extends BaseException {
	private final CongregationNewsExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public CongregationNewsException(CongregationNewsExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
