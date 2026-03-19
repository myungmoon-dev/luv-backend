package org.example.luvbackend.entity.homeworship;

import java.util.UUID;

import org.example.luvbackend.common.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeWorshipComment extends BaseEntity {
	private String id;
	private String userName;
	private String password;
	private String content;

	@Builder
	private HomeWorshipComment(String id, String userName, String password, String content) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.content = content;
	}

	public static HomeWorshipComment of(String userName, String password, String content) {
		return HomeWorshipComment.builder()
			.id(UUID.randomUUID().toString())
			.userName(userName)
			.password(password)
			.content(content)
			.build();
	}
}
