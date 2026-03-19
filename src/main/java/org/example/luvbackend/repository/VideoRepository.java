package org.example.luvbackend.repository;

import java.util.List;

import org.example.luvbackend.entity.video.Video;
import org.example.luvbackend.entity.video.VideoType;
import org.example.luvbackend.exception.video.VideoException;
import org.example.luvbackend.exception.video.VideoExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
	List<Video> findByType(VideoType type);

	default Video findByIdOrElseThrow(String videoId) {
		return findById(videoId)
			.orElseThrow(() -> new VideoException(VideoExceptionCode.NOT_FOUND_VIDEO));
	}
}
