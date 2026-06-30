package org.example.luvbackend.entity.education;

import java.util.ArrayList;
import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "education")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education extends BaseEntity {
	@Id
	private String id;

	@Indexed(unique = true)
	private String type; // infants, toddlers, elementary, high, 2youth, bridge

	private String slug;
	private String department;
	private String heroImageUrl;
	private String heroImgClass;
	private List<String> imageUrls = new ArrayList<>();
	private String introduction;
	private String target;
	private String time;
	private String place;
	private String meetingTime;
	private List<CoreMinistry> coreMinistries = new ArrayList<>();
	private Integer order;

	@Builder
	private Education(String type, String slug, String department, String heroImageUrl, String heroImgClass,
		List<String> imageUrls, String introduction, String target, String time, String place,
		String meetingTime, List<CoreMinistry> coreMinistries, Integer order) {
		this.type = type;
		this.slug = slug;
		this.department = department;
		this.heroImageUrl = heroImageUrl;
		this.heroImgClass = heroImgClass;
		this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
		this.introduction = introduction;
		this.target = target;
		this.time = time;
		this.place = place;
		this.meetingTime = meetingTime;
		this.coreMinistries = coreMinistries != null ? coreMinistries : new ArrayList<>();
		this.order = order;
	}

	public void update(String slug, String department, String heroImageUrl, String heroImgClass,
		List<String> imageUrls, String introduction, String target, String time, String place,
		String meetingTime, List<CoreMinistry> coreMinistries, Integer order) {
		if (slug != null) this.slug = slug;
		if (department != null) this.department = department;
		if (heroImageUrl != null) this.heroImageUrl = heroImageUrl;
		if (heroImgClass != null) this.heroImgClass = heroImgClass;
		if (imageUrls != null) this.imageUrls = imageUrls;
		if (introduction != null) this.introduction = introduction;
		if (target != null) this.target = target;
		if (time != null) this.time = time;
		if (place != null) this.place = place;
		if (meetingTime != null) this.meetingTime = meetingTime;
		if (coreMinistries != null) this.coreMinistries = coreMinistries;
		if (order != null) this.order = order;
	}
}
