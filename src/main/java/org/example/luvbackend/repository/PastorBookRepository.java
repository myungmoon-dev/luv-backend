package org.example.luvbackend.repository;

import org.example.luvbackend.entity.pastorbook.PastorBook;
import org.example.luvbackend.exception.pastorbook.PastorBookException;
import org.example.luvbackend.exception.pastorbook.PastorBookExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastorBookRepository extends MongoRepository<PastorBook, String> {
	Page<PastorBook> findAllByOrderByCreatedAtDesc(Pageable pageable);

	default PastorBook findByIdOrElseThrow(String id) {
		return findById(id)
			.orElseThrow(() -> new PastorBookException(PastorBookExceptionCode.NOT_FOUND_PASTOR_BOOK));
	}
}
