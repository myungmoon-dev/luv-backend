package org.example.luvbackend.dto.homeworship;

import java.time.format.DateTimeFormatter;
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
	private final String createdAt;
	private final String updatedAt;

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
		this.createdAt = homeworship.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.updatedAt = homeworship.getUpdatedAt() != null
			? homeworship.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			: null;
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static HomeWorshipResponseDto from(HomeWorship homeworship) {
		return HomeWorshipResponseDto.builder()
			.homeworship(homeworship)
			.build();
	}
}
