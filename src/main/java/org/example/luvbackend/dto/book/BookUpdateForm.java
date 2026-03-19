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
public class BookUpdateForm {
	@NotBlank(message = "추천도서의 ID는 필수입니다.")
	private String id;

	@NotBlank(message = "제목은 필수 입력값입니다.")
	@Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
	private String title;

	private String content;
	private String writer;
	private LocalDate date;

	// 유지할 기존 이미지 URL 목록 (클라이언트가 삭제하지 않을 이미지 URL을 명시적으로 전달)
	private List<String> existingImageUrls;

	@Size(max = 5, message = "이미지는 최대 5개까지 업로드 가능합니다.")
	private List<MultipartFile> newImages;
}
