package org.example.luvbackend.repository;

import org.example.luvbackend.entity.pastorprofileimage.PastorProfileImage;
import org.example.luvbackend.exception.pastorprofileimage.PastorProfileImageException;
import org.example.luvbackend.exception.pastorprofileimage.PastorProfileImageExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastorProfileImageRepository extends MongoRepository<PastorProfileImage, String> {
	default PastorProfileImage findProfileImageOrElseThrow() {
		return findById("pastor-profile-image")
			.orElseThrow(() -> new PastorProfileImageException(PastorProfileImageExceptionCode.NOT_FOUND_PASTOR_PROFILE_IMAGE));
	}
}