package org.example.luvbackend.entity.homeworship;

import java.time.LocalDateTime;

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
	private LocalDateTime createdAt;

	@Builder
	public HomeWorshipComment(String id, String userName, String password, String content) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.content = content;
		this.createdAt = LocalDateTime.now();
	}
}
