package org.example.luvbackend.dto.pastorprofileimage;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PastorProfileImageForm {
	private MultipartFile topImage;
	private MultipartFile bottomImage;
}
