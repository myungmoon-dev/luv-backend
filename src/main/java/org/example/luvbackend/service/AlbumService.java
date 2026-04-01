package org.example.luvbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.album.AlbumUploadForm;
import org.example.luvbackend.dto.album.AlbumResponseDto;
import org.example.luvbackend.entity.album.Album;
import org.example.luvbackend.entity.album.AlbumType;
import org.example.luvbackend.repository.AlbumRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
	private final AlbumRepository albumRepository;
	private final AwsS3Service awsS3Service;

	/**
	 * 다건 앨범 데이터 조회 메서드
	 */
	@Transactional(readOnly = true)
	public PageResponse<AlbumResponseDto> getAlbums(String type, int page, int size) {
		// 앨범타입을 입력 안했을 경우
		if (type == null || type.isBlank()) {
			Pageable pageable = PageRequest.of(page, size);
			return PageResponse.of(albumRepository.findAllByOrderByCreatedAtDesc(pageable)
					.map(AlbumResponseDto::from));
		}

		AlbumType albumType = AlbumType.deserialize(type);
		return PageResponse.of(albumRepository.findByTypeOrderByCreatedAtDesc(albumType, PageRequest.of(page, size))
				.map(AlbumResponseDto::from)
		);
	}

	/**
	 * 단건 앨범데이터 조회 메서드
	 */
	@Transactional(readOnly = true)
	public AlbumResponseDto getAlbum(String id) {
		return AlbumResponseDto.from(albumRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 앨범 생성 메서드
	 */
	@Transactional
	public AlbumResponseDto createAlbum(AlbumUploadForm requestDto) {
		List<String> imageUrls = awsS3Service.uploadFiles(requestDto.getImages(), S3Directory.ALBUMS);
		return AlbumResponseDto.from(albumRepository.save(Album.of(requestDto, imageUrls)));
	}

	/**
	 * 단건 앨범데이터 삭제 메서드
	 */
	@Transactional
	public void deleteAlbum(String id) {
		Album fromDB = albumRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls()); // 이미지 삭제
		albumRepository.delete(fromDB); // DB 삭제
	}

	/**
	 * 다건 앨범데이터 삭제 메서드
	 */
	@Transactional
	public void deleteAlbums(List<String> ids) {
		List<Album> albums = albumRepository.findAllById(ids);

		// 이미지리스트를 하나의 리스트로 생성
		List<String> imageUrls = albums.stream()
			.flatMap(album -> album.getImageUrls().stream())
			.collect(Collectors.toList());

		awsS3Service.deleteFiles(imageUrls);
		albumRepository.deleteAll(albums);
	}
}
