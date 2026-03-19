package org.example.luvbackend.entity.video;

import org.example.luvbackend.exception.video.VideoException;
import org.example.luvbackend.exception.video.VideoExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VideoType {
	MAIN("main"),
	SHORTS("shorts"),
	YOUTH("youth"),
	AFTERNOON("afternoon"),
	FRIDAY("friday"),
	FIRDAY("firday"), // TypeScript 서버의 오타 데이터 호환
	WEDNESDAY("wednesday"),
	VIDEO("video");

	private final String value;

	public static VideoType deserialize(String type) {
		// VideoException 예외처리
		if (type == null || type.isBlank()) {
			throw new VideoException(VideoExceptionCode.ILLEGAL_VIDEO_TYPE);
		}

		String normalized = type.trim();
		for (VideoType videoType : VideoType.values()) {
			if (videoType.getValue().equalsIgnoreCase(normalized)) {
				return videoType;
			}
		}
		throw new VideoException(VideoExceptionCode.ILLEGAL_VIDEO_TYPE);
	}
}
