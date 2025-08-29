package org.example.luvbackend.exception.cloudflare;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CloudflareException extends RuntimeException {
	private final CloudflareExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public CloudflareException(CloudflareExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
