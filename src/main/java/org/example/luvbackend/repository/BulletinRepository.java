package org.example.luvbackend.repository;

import java.util.List;
import java.util.Optional;

import org.example.luvbackend.entity.bulletin.Bulletin;
import org.example.luvbackend.exception.bulletin.BulletinException;
import org.example.luvbackend.exception.bulletin.BulletinExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository extends MongoRepository<Bulletin, String> {
	Page<Bulletin> findAllByOrderByDateDesc(Pageable pageable);

	Page<Bulletin> findAllByDateStartingWithOrderByDateDesc(String yearMonth, Pageable pageable);

	default Bulletin findByIdOrElseThrow(String bulletinId) {
		return findById(bulletinId)
			.orElseThrow(() -> new BulletinException(BulletinExceptionCode.NOT_FOUND_BULLETIN));
	}

	Optional<Bulletin> findByDate(String date);

	/**
	 * 주보가 존재하는 연도/월 목록 조회
	 * 최대 2,000건 (40년 * 52주) 수준으로 전체 조회해도 성능 이슈 없음
	 */
	@Query(value = "{}", fields = "{'date': 1}")
	List<Bulletin> findAllYearAndDates();
}
