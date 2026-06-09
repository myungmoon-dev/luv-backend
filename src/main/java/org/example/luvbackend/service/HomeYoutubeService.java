package org.example.luvbackend.service;

import org.example.luvbackend.dto.homeyoutube.HomeYoutubeForm;
import org.example.luvbackend.dto.homeyoutube.HomeYoutubeResponseDto;
import org.example.luvbackend.entity.homeyoutube.HomeYoutube;
import org.example.luvbackend.repository.HomeYoutubeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeYoutubeService {
	private final HomeYoutubeRepository homeYoutubeRepository;

	@Transactional(readOnly = true)
	public HomeYoutubeResponseDto getHomeYoutube() {
		return new HomeYoutubeResponseDto(homeYoutubeRepository.findHomeYoutubeOrElseThrow());
	}

	@Transactional
	public HomeYoutubeResponseDto updateHomeYoutube(HomeYoutubeForm form) {
		HomeYoutube homeYoutube = homeYoutubeRepository.findById("home_youtube")
			.orElse(new HomeYoutube(form.getYoutubeUrl()));
		homeYoutube.updateYoutubeUrl(form.getYoutubeUrl());
		return new HomeYoutubeResponseDto(homeYoutubeRepository.save(homeYoutube));
	}
}
