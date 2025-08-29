package org.example.luvbackend.exception.album;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlbumExceptionCode {
	ILLEGAL_ALBUM_TYPE(false, HttpStatus.BAD_REQUEST, "존재하지 않는 앨범타입입니다."),
	NOT_FOUND_ALBUM_OBJECT(false, HttpStatus.NOT_FOUND, "앨범을 찾을 수 없습니다."),
	NO_IMAGE_ALBUM_FORM(false, HttpStatus.BAD_REQUEST, "이미지는 최소1개 이상 업로드해야 합니다."),
	IMAGE_SIZE_OVER_ALBUM_FORM(false, HttpStatus.BAD_REQUEST, "이미지 크기는 10MB를 초과할 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}