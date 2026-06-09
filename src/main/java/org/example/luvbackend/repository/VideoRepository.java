package org.example.luvbackend.repository;

import org.example.luvbackend.entity.video.Video;
import org.example.luvbackend.entity.video.VideoType;
import org.example.luvbackend.exception.video.VideoException;
import org.example.luvbackend.exception.video.VideoExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
	Page<Video> findByTypeOrderByDateDesc(VideoType type, Pageable pageable);

	Page<Video> findAllByOrderByDateDesc(Pageable pageable);

	default Video findByIdOrElseThrow(String videoId) {
		return findById(videoId)
			.orElseThrow(() -> new VideoException(VideoExceptionCode.NOT_FOUND_VIDEO));
	}
}
