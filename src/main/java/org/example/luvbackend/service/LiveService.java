package org.example.luvbackend.service;

import org.example.luvbackend.dto.live.LiveResponseDto;
import org.example.luvbackend.dto.live.LiveUpdateForm;
import org.example.luvbackend.entity.live.Live;
import org.example.luvbackend.repository.LiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiveService {
	private final LiveRepository liveRepository;

	@Transactional(readOnly = true)
	public LiveResponseDto getLive() {
		Live live = liveRepository.findLiveOrElseThrow();
		return new LiveResponseDto(live);
	}

	@Transactional
	public LiveResponseDto updateLive(LiveUpdateForm form) {
		// 기존 live 도큐먼트가 있으면 업데이트, 없으면 새로 생성 (upsert)
		Live live = liveRepository.findById("live")
			.orElse(new Live(form.getUrl()));
		live.updateUrl(form.getUrl());
		return new LiveResponseDto(liveRepository.save(live));
	}
}
