package org.example.luvbackend.entity.pastorprofileimage;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "pastor_profile_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PastorProfileImage extends BaseEntity {
	@Id
	private String id = "pastor-profile-image";

	private String topImageUrl;
	private String bottomImageUrl;

	public PastorProfileImage(String topImageUrl, String bottomImageUrl) {
		this.id = "pastor-profile-image";
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