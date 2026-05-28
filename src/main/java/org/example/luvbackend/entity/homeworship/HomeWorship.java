package org.example.luvbackend.entity.homeworship;

import java.util.ArrayList;
import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.homeworship.HomeWorshipCreateForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "homeworships")
@Getter
@NoArgsConstructor
public class HomeWorship extends BaseEntity {
	@Id
	private String id;

	private String date;
	private String title;
	private String content;
	private List<String> imageUrls;
	private String userName;
	private String password;
	private Boolean isPinned;
	private List<HomeWorshipComment> comments = new ArrayList<>();

	@Builder
	public HomeWorship(String date, String title, String content, List<String> imageUrls,
		String userName, String password, boolean isPinned) {
		this.date = date;
		this.title = title;
		this.content = content;
		this.imageUrls = imageUrls;
		this.userName = userName;
		this.password = password;
		this.isPinned = isPinned;
		this.comments = new ArrayList<>();
	}

	/**
	 * 정적 팩토리 메서드
	 */
	public static HomeWorship of(HomeWorshipCreateForm form, List<String> imageUrls, String hashedPassword) {
		return HomeWorship.builder()
			.date(form.getDate().toString())
			.title(form.getTitle())
			.content(form.getContent())
			.imageUrls(imageUrls)
			.userName(form.getUserName())
			.password(hashedPassword)
			.isPinned(form.isPinned())
			.build();
	}

	/**
	 * 업데이트 메서드 - null인 필드는 기존 값 유지 (PATCH 방식)
	 */
	public void update(String date, String title, String content, String userName, Boolean isPinned, List<String> imageUrls) {
		if (date != null) this.date = date;
		if (title != null) this.title = title;
		if (content != null) this.content = content;
		if (userName != null) this.userName = userName;
		if (isPinned != null) this.isPinned = isPinned;
		if (imageUrls != null && !imageUrls.isEmpty()) this.imageUrls = imageUrls;
	}

	public void addComment(HomeWorshipComment comment) {
		this.comments.add(comment);
	}

	public void removeComment(String commentId) {
		this.comments.removeIf(c -> c.getId().equals(commentId));
	}
}
