package org.example.luvbackend.dto.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateForm {
	private String type;

	@Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
	private String title;

	private String writer;
	private String content;

	// 유지할 기존 이미지 URL 목록 (클라이언트가 삭제하지 않을 URL을 명시적으로 전달)
	private List<String> existingImageUrls;

	// 유지할 기존 파일 URL 목록 (클라이언트가 삭제하지 않을 URL을 명시적으로 전달)
	private List<String> existingFileUrls;

	@Size(max = 5, message = "이미지는 최대 5개까지 업로드 가능합니다.")
	private List<MultipartFile> newImages;

	private List<MultipartFile> newFiles;
}
