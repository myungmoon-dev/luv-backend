package org.example.luvbackend.entity.education;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationHomeCoreValue {
	private String n;
	private String title;

	@Builder
	public EducationHomeCoreValue(String n, String title) {
		this.n = n;
		this.title = title;
	}
}
