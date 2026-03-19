package org.example.luvbackend.exception;

import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseException extends RuntimeException {
	public abstract Enum<?> getErrorCode();
	public abstract HttpStatus getHttpStatus();
	public abstract String getMessage();
}
