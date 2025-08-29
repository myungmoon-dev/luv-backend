package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.album.AlbumUploadForm;
import org.example.luvbackend.dto.album.AlbumResponseDto;
import org.example.luvbackend.entity.album.Album;
import org.example.luvbackend.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
	private final AlbumRepository albumRepository;
	private final CloudflareService cloudflareService;

	@Transactional(readOnly = true)
	public List<AlbumResponseDto> getAlbums() {
		return albumRepository.findAll()
			.stream().map(AlbumResponseDto::new)
			.toList();
	}

	@Transactional(readOnly = true)
	public AlbumResponseDto getAlbum(String id) {
		Album fromDB = albumRepository.findByIdOrElseThrow(id);
		return AlbumResponseDto.builder()
			.album(fromDB)
			.build();
	}

	@Transactional
	public AlbumResponseDto createAlbum(AlbumUploadForm requestDto) {

		List<String> imageUrls = cloudflareService.uploadImages(requestDto.getImages());

		Album album = Album.builder()
			.title(requestDto.getTitle())
			.date(requestDto.getDate())
			.type(requestDto.getType())
			.imageUrls(imageUrls)
			.build();
		albumRepository.save(album);

		return AlbumResponseDto.builder()
			.album(album)
			.build();
	}
}
