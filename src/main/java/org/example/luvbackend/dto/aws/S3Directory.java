package org.example.luvbackend.dto.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3Directory {
	ALBUMS("albums"),
	BOOKS("books"),
	BULLETINS("bulletins"),
	HOMEWORSHIPS("homeworships"),
	MISSION_NEWS("missionNewsList"),
	POPUPS("popups"),
	LEADERSHIP("leadership"),
	HOME_IMAGES("homeContent/images");

	private final String path;
}
