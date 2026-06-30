package org.example.luvbackend.dto.education;

import java.util.List;

import org.example.luvbackend.entity.education.CoreMinistry;
import org.example.luvbackend.entity.education.Education;

import lombok.Getter;

@Getter
public class EducationResponseDto {
	private final String id;
	private final String type;
	private final String slug;
	private final String department;
	private final String heroImageUrl;
	private final String heroImgClass;
	private final List<String> imageUrls;
	private final String introduction;
	private final String target;
	private final String time;
	private final String place;
	private final String meetingTime;
	private final List<CoreMinistryDto> coreMinistries;
	private final Integer order;
	private final Long createdAt;

	private EducationResponseDto(Education e) {
		this.id = e.getId();
		this.type = e.getType();
		this.slug = e.getSlug();
		this.department = e.getDepartment();
		this.heroImageUrl = e.getHeroImageUrl();
		this.heroImgClass = e.getHeroImgClass();
		this.imageUrls = e.getImageUrls();
		this.introduction = e.getIntroduction();
		this.target = e.getTarget();
		this.time = e.getTime();
		this.place = e.getPlace();
		this.meetingTime = e.getMeetingTime();
		this.coreMinistries = e.getCoreMinistries().stream()
			.map(CoreMinistryDto::from)
			.toList();
		this.order = e.getOrder();
		this.createdAt = e.getCreatedAt();
	}

	public static EducationResponseDto from(Education education) {
		return new EducationResponseDto(education);
	}

	@Getter
	public static class CoreMinistryDto {
		private final String titleKr;
		private final String titleEn;
		private final String description;
		private final String imageUrl;
		private final String imgClass;

		private CoreMinistryDto(CoreMinistry c) {
			this.titleKr = c.getTitleKr();
			this.titleEn = c.getTitleEn();
			this.description = c.getDescription();
			this.imageUrl = c.getImageUrl();
			this.imgClass = c.getImgClass();
		}

		public static CoreMinistryDto from(CoreMinistry c) {
			return new CoreMinistryDto(c);
		}
	}
}
