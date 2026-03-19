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
	MISSION_NEWS("mission-news"),
	POPUPS("popups");

	private final String path;
}
