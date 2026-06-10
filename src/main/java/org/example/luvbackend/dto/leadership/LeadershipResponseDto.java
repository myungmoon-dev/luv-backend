package org.example.luvbackend.dto.leadership;

import org.example.luvbackend.entity.leadership.Leadership;

import lombok.Getter;

@Getter
public class LeadershipResponseDto {
	private final String id;
	private final String name;
	private final String imageUrl;
	private final String position;
	private final String officerType;
	private final String tabType;
	private final String greeting;
	private final String description;
	private final Long createdAt;

	private LeadershipResponseDto(Leadership leadership) {
		this.id = leadership.getId();
		this.name = leadership.getName();
		this.imageUrl = leadership.getImageUrl();
		this.position = leadership.getPosition();
		this.officerType = leadership.getOfficerType();
		this.tabType = leadership.getTabType();
		this.greeting = leadership.getGreeting();
		this.description = leadership.getDescription();
		this.createdAt = leadership.getCreatedAt();
	}

	public static LeadershipResponseDto from(Leadership leadership) {
		return new LeadershipResponseDto(leadership);
	}
}