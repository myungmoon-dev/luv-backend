package org.example.luvbackend.exception.album;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlbumExceptionCode {
	ILLEGAL_ALBUM_TYPE(false, HttpStatus.BAD_REQUEST, "존재하지 않는 앨범타입입니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}