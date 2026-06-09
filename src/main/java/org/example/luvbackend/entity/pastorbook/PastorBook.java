package org.example.luvbackend.entity.pastorbook;

import org.example.luvbackend.common.entity.BaseEntity;
import org.example.luvbackend.dto.pastorbook.PastorBookForm;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "pastor_books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PastorBook extends BaseEntity {
	@Id
	private String id;

	private String title;
	private String sub;
	private String publisher;
	private String year;
	private String imageUrl;

	@Builder
	private PastorBook(String title, String sub, String publisher, String year, String imageUrl) {
		this.title = title;
		this.sub = sub;
		this.publisher = publisher;
		this.year = year;
		this.imageUrl = imageUrl;
	}

	public static PastorBook from(PastorBookForm form, String imageUrl) {
		return PastorBook.builder()
			.title(form.getTitle())
			.sub(form.getSub())
			.publisher(form.getPublisher())
			.year(form.getYear())
			.imageUrl(imageUrl)
			.build();
	}

	public void update(PastorBookForm form, String imageUrl) {
		this.title = form.getTitle();
		this.sub = form.getSub();
		this.publisher = form.getPublisher();
		this.year = form.getYear();
		if (imageUrl != null) this.imageUrl = imageUrl;
	}
}