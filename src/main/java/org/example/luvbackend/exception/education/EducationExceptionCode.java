package org.example.luvbackend.exception.education;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EducationExceptionCode {
	NOT_FOUND_EDUCATION(false, HttpStatus.NOT_FOUND, "다음세대 부서를 찾을 수 없습니다."),
	DUPLICATE_TYPE(false, HttpStatus.CONFLICT, "이미 등록된 부서 타입입니다."),
	INVALID_CORE_MINISTRIES(false, HttpStatus.BAD_REQUEST, "핵심 사역 데이터 형식이 올바르지 않습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
