package org.example.luvbackend.repository;

import org.example.luvbackend.entity.homeworship.HomeWorship;
import org.example.luvbackend.exception.homeworship.HomeWorshipException;
import org.example.luvbackend.exception.homeworship.HomeworshipExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworshipRepository extends MongoRepository<HomeWorship, String> {
	Page<HomeWorship> findAllByOrderByDateDesc(Pageable pageable);

	default HomeWorship findByIdOrElseThrow(String homeworshipId) {
		return findById(homeworshipId)
			.orElseThrow(() -> new HomeWorshipException(HomeworshipExceptionCode.NOT_FOUND_HOMEWORSHIP));
	}
}
