package org.example.luvbackend.repository;

import org.example.luvbackend.entity.bible.Bible;
import org.example.luvbackend.exception.bible.BibleException;
import org.example.luvbackend.exception.bible.BibleExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibleRepository extends MongoRepository<Bible, String> {
	Page<Bible> findAllByOrderByDateDesc(Pageable pageable);

	default Bible findByIdOrElseThrow(String bibleId) {
		return findById(bibleId)
			.orElseThrow(() -> new BibleException(BibleExceptionCode.NOT_FOUND_BIBLE));
	}
}
