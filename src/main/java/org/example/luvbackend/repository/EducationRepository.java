package org.example.luvbackend.repository;

import java.util.List;
import java.util.Optional;

import org.example.luvbackend.entity.education.Education;
import org.example.luvbackend.exception.education.EducationException;
import org.example.luvbackend.exception.education.EducationExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends MongoRepository<Education, String> {
	List<Education> findAllByOrderByOrderAsc();

	Optional<Education> findByType(String type);

	boolean existsByType(String type);

	default Education findByIdOrElseThrow(String id) {
		return findById(id)
			.orElseThrow(() -> new EducationException(EducationExceptionCode.NOT_FOUND_EDUCATION));
	}
}
