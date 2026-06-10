package org.example.luvbackend.repository;

import org.example.luvbackend.entity.leadership.Leadership;
import org.example.luvbackend.exception.leadership.LeadershipException;
import org.example.luvbackend.exception.leadership.LeadershipExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadershipRepository extends MongoRepository<Leadership, String> {
	Page<Leadership> findAllByOrderByCreatedAtDesc(Pageable pageable);

	Page<Leadership> findByTabTypeOrderByCreatedAtDesc(String tabType, Pageable pageable);

	default Leadership findByIdOrElseThrow(String id) {
		return findById(id)
			.orElseThrow(() -> new LeadershipException(LeadershipExceptionCode.NOT_FOUND_LEADERSHIP));
	}
}