package org.example.luvbackend.repository;

import org.example.luvbackend.entity.missionnews.MissionNews;
import org.example.luvbackend.exception.missionnews.MissionNewsException;
import org.example.luvbackend.exception.missionnews.MissionNewsExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionNewsRepository extends MongoRepository<MissionNews, String> {
	Page<MissionNews> findAllByOrderByCreatedAtDesc(Pageable pageable);

	Page<MissionNews> findByLocationOrderByCreatedAtDesc(String location, Pageable pageable);

	default MissionNews findByIdOrElseThrow(String missionNewsId) {
		return findById(missionNewsId)
			.orElseThrow(() -> new MissionNewsException(MissionNewsExceptionCode.NOT_FOUND_MISSION_NEWS));
	}
}
