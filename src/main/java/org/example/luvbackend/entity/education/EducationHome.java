package org.example.luvbackend.entity.education;

import java.util.ArrayList;
import java.util.List;

import org.example.luvbackend.common.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "education_home")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationHome extends BaseEntity {
	@Id
	private String id;

	private String heroImageUrl;
	private String heroImgClass;
	private String heroSubtitle; // 예: "일어나라 빛을 발하라!"
	private String missionLine1; // 예: "명문교회 교육부서는"
	private String missionLine2; // 예: "하나님을 경외하는 다음세대를 세우기 위해 존재한다."
	private List<EducationHomeVision> visions = new ArrayList<>();
	private List<EducationHomeCoreValue> coreValues = new ArrayList<>();

	@Builder
	private EducationHome(String heroImageUrl, String heroImgClass, String heroSubtitle,
		String missionLine1, String missionLine2,
		List<EducationHomeVision> visions, List<EducationHomeCoreValue> coreValues) {
		this.heroImageUrl = heroImageUrl;
		this.heroImgClass = heroImgClass;
		this.heroSubtitle = heroSubtitle;
		this.missionLine1 = missionLine1;
		this.missionLine2 = missionLine2;
		this.visions = visions != null ? visions : new ArrayList<>();
		this.coreValues = coreValues != null ? coreValues : new ArrayList<>();
	}

	public void update(String heroImageUrl, String heroImgClass, String heroSubtitle,
		String missionLine1, String missionLine2,
		List<EducationHomeVision> visions, List<EducationHomeCoreValue> coreValues) {
		if (heroImageUrl != null) this.heroImageUrl = heroImageUrl;
		if (heroImgClass != null) this.heroImgClass = heroImgClass;
		if (heroSubtitle != null) this.heroSubtitle = heroSubtitle;
		if (missionLine1 != null) this.missionLine1 = missionLine1;
		if (missionLine2 != null) this.missionLine2 = missionLine2;
		if (visions != null) this.visions = visions;
		if (coreValues != null) this.coreValues = coreValues;
	}
}
