package org.example.luvbackend.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE) // 내부에서만 빌더 사용
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 new 생성 차단
public class ApiResponse<T> {

	private final int httpCode;

	@JsonInclude(JsonInclude.Include.NON_NULL) // null 이면 응답 JSON 에서 생략됨
	private final T data;

	@JsonInclude(JsonInclude.Include.NON_NULL) // null 이면 응답 JSON 에서 생략됨
	private final String message;

	/**
	 * 성공 응답
	 * @param data 결과 데이터
	 */
	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.<T>builder()
			.httpCode(200)
			.data(data)
			.build();
	}

	/**
	 * 데이터가 없는 성공 응답
	 */
	public static <T> ApiResponse<T> noContent() {
		return ApiResponse.<T>builder()
			.httpCode(204)
			.build();
	}

	/**
	 * 생성 성공 응답
	 */
	public static <T> ApiResponse<T> created(T data) {
		return ApiResponse.<T>builder()
			.httpCode(201)
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> fail(int httpCode, String message) {
		return ApiResponse.<T>builder()
			.httpCode(httpCode)
			.message(message)
			.build();
	}
	/**
	 * 커스텀 상태코드가 필요한 경우
	 */
	public static <T> ApiResponse<T> of(int httpCode, T data) {
		return ApiResponse.<T>builder()
			.httpCode(httpCode)
			.data(data)
			.build();
	}
}

