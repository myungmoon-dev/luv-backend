package org.example.luvbackend.exception.aws;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AwsS3Exception extends BaseException {
	private final AwsS3ExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public AwsS3Exception(AwsS3ExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
