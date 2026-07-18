package org.example.luvbackend.exception.board;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardExceptionCode {
	NOT_FOUND_BOARD(false, HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
