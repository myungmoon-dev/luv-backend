package org.example.luvbackend.exception.cloudflare;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CloudflareExceptionCode {

	DIRECT_UPLOAD_REQUEST_ERROR(false, HttpStatus.BAD_REQUEST, "Direct Upload URI 요청에 실패하였습니다."),
	DIRECT_UPLOAD_RESPONSE_ERROR(false, HttpStatus.BAD_REQUEST, "Direct Upload URI 응답에 실패하였습니다."),
	DIRECT_UPLOAD_ID_OR_URL_NOT_FOUND(false, HttpStatus.NOT_FOUND, "imageURL or ID가 누락되었습니다."),
	DIRECT_UPLOAD_JSON_PARSING_ERROR(false, HttpStatus.BAD_REQUEST, "JSON 파싱이 제대로 되지 않았습니다."),

	CLOUDFLARE_UPLOAD_FAILED(false, HttpStatus.BAD_REQUEST, "이미지 업로드가 실패하였습니다. 용량을 확인해주세요."),
	CLOUDFLARE_VARIANTS_NOT_FOUND(false, HttpStatus.NOT_FOUND, "variants를 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
