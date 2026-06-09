package org.example.luvbackend.entity.homeyoutube;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "homeYoutube")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeYoutube extends BaseEntity {
	@Id
	private String id = "home_youtube";

	private String youtubeUrl;

	public HomeYoutube(String youtubeUrl) {
		this.id = "home_youtube";
		this.youtubeUrl = youtubeUrl;
	}

	public void updateYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}
}
