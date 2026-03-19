package org.example.luvbackend.entity.bible;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BibleLink {
	private String url;
	private boolean isPlaylist;
}
