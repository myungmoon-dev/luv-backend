package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.congregationnews.CongregationNewsCreateForm;
import org.example.luvbackend.dto.congregationnews.CongregationNewsResponseDto;
import org.example.luvbackend.dto.congregationnews.CongregationNewsUpdateForm;
import org.example.luvbackend.entity.congregationnews.CongregationNews;
import org.example.luvbackend.entity.congregationnews.CongregationNewsType;
import org.example.luvbackend.repository.CongregationNewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CongregationNewsService {
	private final CongregationNewsRepository congregationNewsRepository;

	/**
	 * 다건 타입별 교우소식 조회
	 */
	@Transactional(readOnly = true)
	public List<CongregationNewsResponseDto> getCongregationNewsList(String type) {
		if (type != null && !type.isBlank()) {
			CongregationNewsType newsType = CongregationNewsType.deserialize(type);
			return congregationNewsRepository.findByTypeOrderByCreatedAtDesc(newsType).stream()
				.map(CongregationNewsResponseDto::from)
				.toList();
		}
		return congregationNewsRepository.findAll().stream()
			.map(CongregationNewsResponseDto::from)
			.toList();
	}

	/**
	 * 단건 교우소식 조회
	 */
	@Transactional(readOnly = true)
	public CongregationNewsResponseDto getCongregationNews(String id) {
		return CongregationNewsResponseDto.from(congregationNewsRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 교우소식 생성
	 */
	@Transactional
	public CongregationNewsResponseDto createCongregationNews(CongregationNewsCreateForm form) {
		return CongregationNewsResponseDto.from(congregationNewsRepository.save(CongregationNews.of(form)));
	}

	/**
	 * 단건 교우소식 수정
	 */
	@Transactional
	public CongregationNewsResponseDto updateCongregationNews(String id, CongregationNewsUpdateForm form) {
		CongregationNews news = congregationNewsRepository.findByIdOrElseThrow(id);
		// type이 전달된 경우에만 역직렬화, null이면 기존 값 유지
		CongregationNewsType newsType = form.getType() != null ? CongregationNewsType.deserialize(form.getType()) : null;
		news.update(newsType, form.getDescription());
		return CongregationNewsResponseDto.from(congregationNewsRepository.save(news));
	}

	/**
	 * 단건 교우소식 삭제
	 */
	@Transactional
	public void deleteCongregationNews(String id) {
		congregationNewsRepository.delete(congregationNewsRepository.findByIdOrElseThrow(id));
	}
}
