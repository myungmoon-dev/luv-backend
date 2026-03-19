package org.example.luvbackend.dto.homeworship;

import java.util.List;

import org.example.luvbackend.entity.homeworship.HomeWorship;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HomeWorshipResponseDto {
	private final String id;
	private final String date;
	private final String title;
	private final String content;
	private final List<String> imageUrls;
	private final String userName;
	private final Boolean isPinned;
	private final List<HomeWorshipCommentResponseDto> comments;
	private final Long createdAt;
	private final Long updatedAt;

	@Builder(access = AccessLevel.PRIVATE)
	private HomeWorshipResponseDto(HomeWorship homeworship) {
		this.id = homeworship.getId();
		this.date = homeworship.getDate();
		this.title = homeworship.getTitle();
		this.content = homeworship.getContent();
		this.imageUrls = homeworship.getImageUrls();
		this.userName = homeworship.getUserName();
		this.isPinned = homeworship.getIsPinned();
		this.comments = homeworship.getComments().stream()
			.map(HomeWorshipCommentResponseDto::from)
			.toList();
		this.createdAt = homeworship.getCreatedAt();
		this.updatedAt = homeworship.getUpdatedAt();
	}

	public static HomeWorshipResponseDto from(HomeWorship homeworship) {
		return HomeWorshipResponseDto.builder()
			.homeworship(homeworship)
			.build();
	}
}
