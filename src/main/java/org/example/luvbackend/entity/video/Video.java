package org.example.luvbackend.entity.video;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.video.VideoCreateForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "videos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseEntity {
	@Id
	private String id;

	private String url;
	private String title;
	private String date;
	private String mainText;
	private String preacher;
	private VideoType type;

	public String getType() {
		return type != null ? type.getValue() : null;
	}

	@Builder
	private Video(String url, String title, String date, String mainText, String preacher, VideoType type) {
		this.url = url;
		this.title = title;
		this.date = date;
		this.mainText = mainText;
		this.preacher = preacher;
		this.type = type;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static Video from(VideoCreateForm form) {
		return Video.builder()
			.url(form.getUrl())
			.title(form.getTitle())
			.date(form.getDate())
			.mainText(form.getMainText())
			.preacher(form.getPreacher())
			.type(VideoType.deserialize(form.getType()))
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(String url, String title, String date, String mainText, String preacher, VideoType type) {
		if (url != null) this.url = url;
		if (title != null) this.title = title;
		if (date != null) this.date = date;
		if (mainText != null) this.mainText = mainText;
		if (preacher != null) this.preacher = preacher;
		if (type != null) this.type = type;
	}
}
