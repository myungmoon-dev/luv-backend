package org.example.luvbackend.dto.album;

import java.time.LocalDate;
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
public class AlbumUpdateForm {
	@Size(max = 100, message = "앨범제목은 100자를 넘을 수 없습니다.")
	private String title;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private String type;

	// 유지할 기존 이미지 URL 목록
	private List<String> existingImageUrls;

	@Size(max = 5, message = "이미지는 최대 5개까지 업로드 가능합니다.")
	private List<MultipartFile> images;
}