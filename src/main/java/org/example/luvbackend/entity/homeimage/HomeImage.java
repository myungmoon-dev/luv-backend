package org.example.luvbackend.entity.homeimage;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "home_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeImage extends BaseEntity {
	@Id
	private String id;

	private String imageUrl;

	public HomeImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}