package org.example.luvbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.common.util.FileUtils;
import org.example.luvbackend.dto.album.AlbumResponseDto;
import org.example.luvbackend.dto.album.AlbumUpdateForm;
import org.example.luvbackend.dto.album.AlbumUploadForm;
import org.example.luvbackend.entity.album.Album;
import org.example.luvbackend.entity.album.AlbumType;
import org.example.luvbackend.repository.AlbumRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		if (type == null || type.isBlank()) {
			Pageable pageable = PageRequest.of(page, size);
			return PageResponse.of(albumRepository.findAllByOrderByDateDesc(pageable)
					.map(AlbumResponseDto::from));
		}

		AlbumType albumType = AlbumType.deserialize(type);
		return PageResponse.of(albumRepository.findByTypeOrderByDateDesc(albumType, PageRequest.of(page, size))
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
	public AlbumResponseDto createAlbum(AlbumUploadForm form) {
		List<String> keys = buildAlbumKeys(form.getType(), form.getDate().toString(), form.getImages(), 1);
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), keys);
		return AlbumResponseDto.from(albumRepository.save(Album.of(form, imageUrls)));
	}

	/**
	 * 단건 앨범 수정 메서드
	 */
	@Transactional
	public AlbumResponseDto updateAlbum(String id, AlbumUpdateForm form) {
		Album album = albumRepository.findByIdOrElseThrow(id);

		// 기존 이미지 중 삭제된 URL 계산
		List<String> existingUrls = form.getExistingImageUrls() != null ? form.getExistingImageUrls() : List.of();
		List<String> removedUrls = album.getImageUrls().stream()
			.filter(url -> !existingUrls.contains(url))
			.collect(Collectors.toList());

		String albumType = form.getType() != null ? form.getType() : album.getType();
		String albumDate = form.getDate() != null ? form.getDate().toString() : album.getDate();
		List<String> keys = buildAlbumKeys(albumType, albumDate, form.getImages(), existingUrls.size() + 1);
		List<String> uploadedUrls = awsS3Service.uploadFiles(form.getImages(), keys);
		try {
			List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), uploadedUrls);
			AlbumType type = form.getType() != null ? AlbumType.deserialize(form.getType()) : null;
			album.update(form.getTitle(), albumDate, type, mergedImageUrls);
			AlbumResponseDto result = AlbumResponseDto.from(albumRepository.save(album));
			awsS3Service.deleteFiles(removedUrls); // DB 저장 성공 후 S3에서 삭제
			return result;
		} catch (Exception e) {
			awsS3Service.deleteFiles(uploadedUrls);
			throw e;
		}
	}

	/**
	 * 단건 앨범데이터 삭제 메서드
	 */
	@Transactional
	public void deleteAlbum(String id) {
		Album fromDB = albumRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls());
		albumRepository.delete(fromDB);
	}

	/**
	 * 다건 앨범데이터 삭제 메서드
	 */
	@Transactional
	public void deleteAlbums(List<String> ids) {
		List<Album> albums = albumRepository.findAllById(ids);

		List<String> imageUrls = albums.stream()
			.flatMap(album -> album.getImageUrls().stream())
			.collect(Collectors.toList());

		awsS3Service.deleteFiles(imageUrls);
		albumRepository.deleteAll(albums);
	}

	// albums/{type}/{date}/{uuid}/01.jpg 형태로 key 생성
	private List<String> buildAlbumKeys(String type, String date, List<MultipartFile> files, int startIndex) {
		if (files == null || files.isEmpty()) return List.of();
		String uuid = UUID.randomUUID().toString();
		List<String> keys = new ArrayList<>();
		int index = startIndex;
		for (MultipartFile file : files) {
			keys.add(String.format("albums/%s/%s/%s/%02d%s", type, date, uuid, index++,
				FileUtils.extractExtension(file.getOriginalFilename())));
		}
		return keys;
	}
}