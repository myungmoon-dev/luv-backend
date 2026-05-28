package org.example.luvbackend.entity.homeworship;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeWorshipComment {
	private String id;
	private String userName;
	private String password;
	private String content;
	private Long createdAt;

	@Builder
	private HomeWorshipComment(String id, String userName, String password, String content, Long createdAt) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static HomeWorshipComment of(String userName, String password, String content) {
		return HomeWorshipComment.builder()
			.id(UUID.randomUUID().toString())
			.userName(userName)
			.password(password)
			.content(content)
			.createdAt(System.currentTimeMillis())
			.build();
	}
}
