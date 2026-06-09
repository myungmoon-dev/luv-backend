package org.example.luvbackend.repository;

import org.example.luvbackend.entity.homeyoutube.HomeYoutube;
import org.example.luvbackend.exception.homeyoutube.HomeYoutubeException;
import org.example.luvbackend.exception.homeyoutube.HomeYoutubeExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeYoutubeRepository extends MongoRepository<HomeYoutube, String> {
	default HomeYoutube findHomeYoutubeOrElseThrow() {
		return findById("home_youtube")
			.orElseThrow(() -> new HomeYoutubeException(HomeYoutubeExceptionCode.NOT_FOUND_HOME_YOUTUBE));
	}
}
