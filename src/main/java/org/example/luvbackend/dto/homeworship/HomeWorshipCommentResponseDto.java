package org.example.luvbackend.dto.homeworship;

import java.time.format.DateTimeFormatter;

import org.example.luvbackend.entity.homeworship.HomeWorshipComment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HomeWorshipCommentResponseDto {
	private final String id;
	private final String userName;
	private final String content;
	private final String createdAt;

	@Builder(access = AccessLevel.PRIVATE)
	private HomeWorshipCommentResponseDto(HomeWorshipComment comment) {
		this.id = comment.getId();
		this.userName = comment.getUserName();
		this.content = comment.getContent();
		this.createdAt = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static HomeWorshipCommentResponseDto from(HomeWorshipComment comment) {
		return HomeWorshipCommentResponseDto.builder()
			.comment(comment)
			.build();
	}
}
