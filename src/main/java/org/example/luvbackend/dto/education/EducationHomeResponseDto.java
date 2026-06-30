package org.example.luvbackend.dto.education;

import java.util.List;

import org.example.luvbackend.entity.education.EducationHome;
import org.example.luvbackend.entity.education.EducationHomeCoreValue;
import org.example.luvbackend.entity.education.EducationHomeVision;

import lombok.Getter;

@Getter
public class EducationHomeResponseDto {
	private final String id;
	private final String heroImageUrl;
	private final String heroImgClass;
	private final String heroSubtitle;
	private final String missionLine1;
	private final String missionLine2;
	private final List<VisionDto> visions;
	private final List<CoreValueDto> coreValues;

	private EducationHomeResponseDto(EducationHome home) {
		this.id = home.getId();
		this.heroImageUrl = home.getHeroImageUrl();
		this.heroImgClass = home.getHeroImgClass();
		this.heroSubtitle = home.getHeroSubtitle();
		this.missionLine1 = home.getMissionLine1();
		this.missionLine2 = home.getMissionLine2();
		this.visions = home.getVisions().stream().map(VisionDto::from).toList();
		this.coreValues = home.getCoreValues().stream().map(CoreValueDto::from).toList();
	}

	public static EducationHomeResponseDto from(EducationHome home) {
		return new EducationHomeResponseDto(home);
	}

	@Getter
	public static class VisionDto {
		private final String lead;
		private final String emphasis;
		private final String bold;

		private VisionDto(EducationHomeVision v) {
			this.lead = v.getLead();
			this.emphasis = v.getEmphasis();
			this.bold = v.getBold();
		}

		public static VisionDto from(EducationHomeVision v) {
			return new VisionDto(v);
		}
	}

	@Getter
	public static class CoreValueDto {
		private final String n;
		private final String title;

		private CoreValueDto(EducationHomeCoreValue c) {
			this.n = c.getN();
			this.title = c.getTitle();
		}

		public static CoreValueDto from(EducationHomeCoreValue c) {
			return new CoreValueDto(c);
		}
	}
}
