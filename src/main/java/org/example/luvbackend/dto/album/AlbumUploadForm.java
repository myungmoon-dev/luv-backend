package org.example.luvbackend.dto.album;

import java.util.List;

import org.example.luvbackend.entity.album.AlbumType;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AlbumUploadForm {
	@NotBlank(message = "앨범제목은 필수 입력값입니다.")
	@Size(max = 100, message = "앨범제목은 100자를 넘을 수 없습니다.")
	private String title;

	@NotBlank(message = "앨범날짜는 필수 입력값입니다.")
	private String date;

	@NotNull(message = "앨범종류는 필수 입력값입니다.")
	private AlbumType type;

	@NotEmpty(message = "이미지는 최소 1개이상 업로드해야합니다.")
	@Size(min = 1, max = 5, message = "이미지는 최소1개, 최대5개까지 한번에 업로드 가능합니다.")
	private List<MultipartFile> images;
}
