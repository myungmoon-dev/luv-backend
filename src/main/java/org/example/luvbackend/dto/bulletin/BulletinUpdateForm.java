package org.example.luvbackend.dto.bulletin;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BulletinUpdateForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private MultipartFile pdf;
}