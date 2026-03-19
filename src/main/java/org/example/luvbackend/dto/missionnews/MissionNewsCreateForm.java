package org.example.luvbackend.dto.missionnews;

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
public class MissionNewsCreateForm {
	@NotBlank(message = "제목은 필수 입력값입니다.")
	@Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
	private String title;

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;

	@NotBlank(message = "작성자 이름은 필수 입력값입니다.")
	private String userName;

	@NotNull(message = "날짜는 필수 입력값입니다.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotBlank(message = "지역은 필수 입력값입니다.")
	private String location;

	@Size(max = 10, message = "이미지는 최대 10개까지 업로드 가능합니다.")
	private List<MultipartFile> images;
}
