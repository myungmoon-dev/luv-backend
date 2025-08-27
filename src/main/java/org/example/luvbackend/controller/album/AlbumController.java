package org.example.luvbackend.controller.album;

import java.util.List;

import org.example.luvbackend.dto.AlbumResponseDto;
import org.example.luvbackend.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {
	private final AlbumService albumService;

	@GetMapping
	public List<AlbumResponseDto> getAlbums() {
		return albumService.getAlbums();
	}
}
