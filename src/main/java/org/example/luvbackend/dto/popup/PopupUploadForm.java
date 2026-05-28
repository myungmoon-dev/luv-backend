package org.example.luvbackend.dto.popup;

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
public class PopupUploadForm {
	@NotBlank(message = "팝업 제목은 필수 입력값입니다.")
	@Size(max = 100, message = "팝업 제목은 100자를 넘을 수 없습니다.")
	private String title;

	@NotNull(message = "이미지는 필수 입력값입니다.")
	private MultipartFile image;

	@NotNull(message = "공개여부는 필수 입력값입니다.")
	private Boolean isShow;
}
