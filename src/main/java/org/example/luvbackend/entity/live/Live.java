package org.example.luvbackend.entity.live;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "live")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Live extends BaseEntity {
	@Id
	private String id = "live";

	private String url;

	public Live(String url) {
		this.id = "live";
		this.url = url;
	}

	public void updateUrl(String url) {
		this.url = url;
	}
}
