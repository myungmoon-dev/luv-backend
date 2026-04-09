package org.example.luvbackend.dto.missionnews;

import java.time.YearMonth;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionNewsUpdateForm {
	// PATCH 방식 - 수정할 필드만 전달, null인 필드는 기존 값 유지
	@Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
	private String title;

	private String content;
	private String userName;

	@DateTimeFormat(pattern = "yyyy-MM")
	private YearMonth date;

	private String location;

	// 유지할 기존 이미지 URL 목록 (클라이언트가 삭제하지 않을 이미지 URL을 명시적으로 전달)
	private List<String> existingImageUrls;

	@Size(max = 10, message = "이미지는 최대 10개까지 업로드 가능합니다.")
	private List<MultipartFile> images;
}
