package org.example.luvbackend.exception.video;

import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class VideoException extends BaseException {
	private final VideoExceptionCode errorCode;
	private final HttpStatus httpStatus;
	private final String message;

	public VideoException(VideoExceptionCode errorCode) {
		this.errorCode = errorCode;
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}
}
