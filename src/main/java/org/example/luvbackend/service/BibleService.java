package org.example.luvbackend.service;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.bible.BibleCreateForm;
import org.example.luvbackend.dto.bible.BibleResponseDto;
import org.example.luvbackend.entity.bible.Bible;
import org.example.luvbackend.repository.BibleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BibleService {
	private final BibleRepository bibleRepository;

	/**
	 * 다건 페이징 성경통독 조회
	 */
	@Transactional(readOnly = true)
	public PageResponse<BibleResponseDto> getBibles(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return PageResponse.of(bibleRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(BibleResponseDto::from));
	}

	/**
	 * 단건 성경통독 조회
	 */
	@Transactional(readOnly = true)
	public BibleResponseDto getBible(String id) {
		return BibleResponseDto.from(bibleRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 성경통독 생성
	 */
	@Transactional
	public BibleResponseDto createBible(BibleCreateForm form) {
		Bible bible = Bible.from(form);
		return BibleResponseDto.from(bibleRepository.save(bible));
	}

	/**
	 * 단건 성경통독 삭제
	 */
	@Transactional
	public void deleteBible(String id) {
		bibleRepository.delete(bibleRepository.findByIdOrElseThrow(id));
	}
}
