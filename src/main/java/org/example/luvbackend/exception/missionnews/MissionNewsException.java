package org.example.luvbackend.exception.missionnews;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MissionNewsException extends BaseException {
	private final MissionNewsExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public MissionNewsException(MissionNewsExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
