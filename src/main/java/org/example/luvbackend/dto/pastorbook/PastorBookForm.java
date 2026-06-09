package org.example.luvbackend.dto.pastorbook;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PastorBookForm {
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String title;

	private String sub;

	@NotBlank(message = "출판사는 필수 입력값입니다.")
	private String publisher;

	@NotBlank(message = "출판연도는 필수 입력값입니다.")
	private String year;

	private MultipartFile image;
}