package org.example.luvbackend.entity.album;

import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "albums")
@Getter
@NoArgsConstructor
public class Album extends BaseEntity {
	@Id
	private String id;

	private String title;
	private String date;
	private AlbumType type; // DB 에는 AlbumType.value()로 저장되고 있음
	private List<String> imageUrls;

	public String getType() {
		return type.getValue();
	}

	@Builder
	public Album(String title, String date, AlbumType type, List<String> imageUrls) {
		this.title = title;
		this.date = date;
		this.type = type;
		this.imageUrls = imageUrls;
	}
}
