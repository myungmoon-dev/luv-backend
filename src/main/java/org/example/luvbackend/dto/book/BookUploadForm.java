package org.example.luvbackend.dto.book;

import java.time.LocalDate;
import java.util.List;

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
public class BookUploadForm {
	@NotBlank(message = "제목은 필수 입력값입니다.")
	@Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
	private String title;

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;

	@NotBlank(message = "작성자는 필수 입력값입니다.")
	private String writer;

	@NotNull(message = "날짜는 필수 입력값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@Size(max = 5, message = "이미지는 최대 5개까지 업로드 가능합니다.")
	private List<MultipartFile> images;
}
