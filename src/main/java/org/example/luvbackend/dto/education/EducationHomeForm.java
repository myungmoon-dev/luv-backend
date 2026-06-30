package org.example.luvbackend.dto.education;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EducationHomeForm {
	private String heroImgClass;
	private String heroSubtitle;
	private String missionLine1;
	private String missionLine2;
	/** JSON 문자열: [{lead, emphasis, bold}] */
	private String visionsJson;
	/** JSON 문자열: [{n, title}] */
	private String coreValuesJson;
	private MultipartFile heroImage;
}
