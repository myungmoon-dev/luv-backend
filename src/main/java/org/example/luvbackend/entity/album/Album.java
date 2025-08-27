package org.example.luvbackend.entity.album;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private AlbumType type;
	private String[] imgUrls;

	public String getType() {
		return type.getValue();
	}
}
