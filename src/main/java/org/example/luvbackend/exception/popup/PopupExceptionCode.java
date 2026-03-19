package org.example.luvbackend.exception.popup;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupExceptionCode {
	NOT_FOUND_POPUP(false, HttpStatus.NOT_FOUND, "팝업을 찾을 수 없습니다."),
	NO_IMAGE_POPUP_FORM(false, HttpStatus.BAD_REQUEST, "이미지는 1개 업로드해야 합니다."),
	IMAGE_SIZE_OVER_FORM(false, HttpStatus.BAD_REQUEST, "이미지 크기는 10MB를 초과할 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
