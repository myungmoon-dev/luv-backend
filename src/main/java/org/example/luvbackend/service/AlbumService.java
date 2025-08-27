package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.AlbumResponseDto;
import org.example.luvbackend.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
	private final AlbumRepository albumRepository;

	public List<AlbumResponseDto> getAlbums() {
		return albumRepository.findAll()
			.stream().map(AlbumResponseDto::new)
			.toList();
	}
}
