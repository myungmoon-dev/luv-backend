package org.example.luvbackend.entity.education;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationHomeVision {
	private String lead;
	private String emphasis;
	private String bold;

	@Builder
	public EducationHomeVision(String lead, String emphasis, String bold) {
		this.lead = lead;
		this.emphasis = emphasis;
		this.bold = bold;
	}
}
