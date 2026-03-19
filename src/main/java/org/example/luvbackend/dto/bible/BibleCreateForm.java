package org.example.luvbackend.dto.bible;

import java.time.LocalDate;
import java.util.List;

import org.example.luvbackend.entity.bible.BibleLink;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BibleCreateForm {
	@NotBlank(message = "제목은 필수 입력값입니다.")
	@jakarta.validation.constraints.Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
	private String title;

	@NotNull(message = "날짜는 필수 입력값입니다.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;

	private List<BibleLink> links;
}
