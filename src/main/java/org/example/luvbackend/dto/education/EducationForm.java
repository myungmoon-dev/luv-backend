package org.example.luvbackend.dto.education;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EducationForm {
	@NotBlank
	private String type;

	private String slug;
	private String department;
	private String heroImgClass;
	private String introduction;
	private String target;
	private String time;
	private String place;
	private String meetingTime;
	private String order;

	/** 배너 이미지 */
	private MultipartFile heroImage;

	/** 상단 이미지들 (기존 유지할 URL 목록) */
	private List<String> existingImageUrls;

	/** 새로 업로드할 상단 이미지들 */
	private List<MultipartFile> images;

	/** 핵심 사역 메타데이터 (JSON 문자열) — 형식: CoreMinistryInput 배열 */
	private String coreMinistriesJson;

	/** 핵심 사역 이미지들 — 인덱스 순서대로 코어 사역에 매핑 (없으면 imageUrl 유지) */
	private List<MultipartFile> coreMinistryImages;

	/** coreMinistryImages 와 1:1 매핑되는 인덱스. ex: [0, 2] → 0번/2번 코어사역의 이미지 교체 */
	private List<Integer> coreMinistryImageIndexes;
}
