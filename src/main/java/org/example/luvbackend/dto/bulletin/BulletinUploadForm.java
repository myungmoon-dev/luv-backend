package org.example.luvbackend.dto.bulletin;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BulletinUploadForm {
	@NotBlank(message = "주보 제목은 필수 입력값입니다.")
	@Size(max = 100, message = "주보 제목은 100자를 넘을 수 없습니다.")
	private String title;

	@NotNull(message = "날짜는 필수 입력값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotNull(message = "PDF 파일은 필수 입력값입니다.")
	private MultipartFile pdf;
}