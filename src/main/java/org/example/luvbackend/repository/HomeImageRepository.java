package org.example.luvbackend.repository;

import org.example.luvbackend.entity.homeimage.HomeImage;
import org.example.luvbackend.exception.homeimage.HomeImageException;
import org.example.luvbackend.exception.homeimage.HomeImageExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeImageRepository extends MongoRepository<HomeImage, String> {
	Page<HomeImage> findAllByOrderByCreatedAtDesc(Pageable pageable);

	default HomeImage findByIdOrElseThrow(String id) {
		return findById(id)
			.orElseThrow(() -> new HomeImageException(HomeImageExceptionCode.NOT_FOUND_HOME_IMAGE));
	}
}