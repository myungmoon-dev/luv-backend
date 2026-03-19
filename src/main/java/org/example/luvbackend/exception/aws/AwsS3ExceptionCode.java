package org.example.luvbackend.exception.aws;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AwsS3ExceptionCode {
	FILE_UPLOAD_FAILED(false, HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 업로드에 실패했습니다."),
	FILE_DOWNLOAD_FAILED(false, HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 다운로드에 실패했습니다."),
	FILE_DELETE_FAILED(false, HttpStatus.INTERNAL_SERVER_ERROR, "S3 파일 삭제에 실패했습니다.");

	private final boolean isSuccess;
	private final HttpStatus httpStatus;
	private final String message;
}
