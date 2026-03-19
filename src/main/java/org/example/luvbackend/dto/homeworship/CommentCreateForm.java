package org.example.luvbackend.dto.homeworship;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreateForm {
	@NotBlank(message = "작성자 이름은 필수 입력값입니다.")
	private String userName;

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	private String password;

	@NotBlank(message = "댓글 내용은 필수 입력값입니다.")
	private String content;
}
