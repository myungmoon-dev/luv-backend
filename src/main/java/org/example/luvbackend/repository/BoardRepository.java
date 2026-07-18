package org.example.luvbackend.repository;

import org.example.luvbackend.entity.board.Board;
import org.example.luvbackend.exception.board.BoardException;
import org.example.luvbackend.exception.board.BoardExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {
	Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);

	Page<Board> findByTypeOrderByCreatedAtDesc(String type, Pageable pageable);

	default Board findByIdOrElseThrow(String boardId) {
		return findById(boardId)
			.orElseThrow(() -> new BoardException(BoardExceptionCode.NOT_FOUND_BOARD));
	}
}
