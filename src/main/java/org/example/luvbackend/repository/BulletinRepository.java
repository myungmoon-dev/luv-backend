package org.example.luvbackend.repository;

import org.example.luvbackend.entity.bulletin.Bulletin;
import org.example.luvbackend.exception.bulletin.BulletinException;
import org.example.luvbackend.exception.bulletin.BulletinExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository extends MongoRepository<Bulletin, String> {
	Page<Bulletin> findAllByOrderByDateDesc(Pageable pageable);

	default Bulletin findByIdOrElseThrow(String bulletinId) {
		return findById(bulletinId)
			.orElseThrow(() -> new BulletinException(BulletinExceptionCode.NOT_FOUND_BULLETIN));
	}
}
