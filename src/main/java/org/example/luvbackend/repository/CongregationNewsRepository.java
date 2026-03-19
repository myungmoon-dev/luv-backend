package org.example.luvbackend.repository;

import java.util.List;

import org.example.luvbackend.entity.congregationnews.CongregationNews;
import org.example.luvbackend.entity.congregationnews.CongregationNewsType;
import org.example.luvbackend.exception.congregationnews.CongregationNewsException;
import org.example.luvbackend.exception.congregationnews.CongregationNewsExceptionCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongregationNewsRepository extends MongoRepository<CongregationNews, String> {
	List<CongregationNews> findByTypeOrderByCreatedAtDesc(CongregationNewsType type);

	default CongregationNews findByIdOrElseThrow(String congregationNewsId) {
		return findById(congregationNewsId)
			.orElseThrow(() ->
				new CongregationNewsException(CongregationNewsExceptionCode.NOT_FOUND_CONGREGATION_NEWS));
	}
}
