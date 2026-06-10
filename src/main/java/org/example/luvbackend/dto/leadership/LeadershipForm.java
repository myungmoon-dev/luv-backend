package org.example.luvbackend.dto.leadership;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LeadershipForm {
	@NotBlank
	private String tabType;

	private String name;
	private String officerType;
	private String position;
	private String greeting;
	private String description;
	private String order;
	private MultipartFile image;
}