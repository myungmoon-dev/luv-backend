package org.example.luvbackend.repository;

import org.example.luvbackend.entity.book.Book;
import org.example.luvbackend.exception.book.BookException;
import org.example.luvbackend.exception.book.BookExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
	Page<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);

	default Book findByIdOrElseThrow(String bookId) {
		return findById(bookId)
			.orElseThrow(() -> new BookException(BookExceptionCode.NOT_FOUND_BOOK));
	}
}
