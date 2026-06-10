package org.example.luvbackend.exception.leadership;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class LeadershipException extends BaseException {
	private final LeadershipExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public LeadershipException(LeadershipExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}