package org.example.luvbackend.entity.pastorprofile;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "pastor_profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PastorProfile extends BaseEntity {
	@Id
	private String id = "pastor-profile";

	private String topImageUrl;
	private String bottomImageUrl;

	public PastorProfile(String topImageUrl, String bottomImageUrl) {
		this.id = "pastor-profile";
		this.topImageUrl = topImageUrl;
		this.bottomImageUrl = bottomImageUrl;
	}

	public void updateTopImageUrl(String topImageUrl) {
		this.topImageUrl = topImageUrl;
	}

	public void updateBottomImageUrl(String bottomImageUrl) {
		this.bottomImageUrl = bottomImageUrl;
	}
}
