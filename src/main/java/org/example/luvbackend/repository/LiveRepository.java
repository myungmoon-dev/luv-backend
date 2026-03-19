package org.example.luvbackend.repository;

import org.example.luvbackend.entity.live.Live;
import org.example.luvbackend.exception.live.LiveException;
import org.example.luvbackend.exception.live.LiveExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveRepository extends MongoRepository<Live, String> {
	default Live findLiveOrElseThrow() {
		return findById("live")
			.orElseThrow(() -> new LiveException(LiveExceptionCode.NOT_FOUND_LIVE));
	}
}
