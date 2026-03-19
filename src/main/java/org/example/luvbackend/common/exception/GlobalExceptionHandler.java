package org.example.luvbackend.common.exception;

import org.example.luvbackend.common.dto.ApiResponse;
import org.example.luvbackend.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 로직 예외처리
	 */
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {
		log.warn("BaseException: {}", e.getMessage());

		return ResponseEntity
			.status(e.getHttpStatus())
			.body(ApiResponse.fail(e.getHttpStatus().value(), e.getMessage()));
	}

	/**
	 * 유효성 검사 예외처리
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {

		String message = e.getBindingResult().getFieldErrors().stream()
			.map(err -> err.getField() + ": " + err.getDefaultMessage())
			.findFirst()
			.orElse("입력값이 올바르지 않습니다.");

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ApiResponse.fail(400, message));
	}

	/**
	 * 위에서 처리하지 못한 예외처리
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		log.error("UnhandledException: {}", e.getMessage(), e);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponse.fail(500, "서버 오류가 발생했습니다."));
	}
}
