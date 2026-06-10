package org.example.luvbackend.entity.leadership;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "leadership")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Leadership extends BaseEntity {
	@Id
	private String id;

	private String name;
	private String imageUrl;
	private String position;
	private String officerType;
	private String tabType;
	private String greeting;
	private String description;

	@Builder
	private Leadership(String name, String imageUrl, String position, String officerType,
		String tabType, String greeting, String description) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.position = position;
		this.officerType = officerType;
		this.tabType = tabType;
		this.greeting = greeting;
		this.description = description;
	}

	public void update(String name, String imageUrl, String position, String officerType,
		String greeting, String description) {
		if (name != null) this.name = name;
		if (imageUrl != null) this.imageUrl = imageUrl;
		if (position != null) this.position = position;
		if (officerType != null) this.officerType = officerType;
		if (greeting != null) this.greeting = greeting;
		if (description != null) this.description = description;
	}
}