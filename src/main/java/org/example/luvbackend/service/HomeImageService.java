package org.example.luvbackend.service;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.homeimage.HomeImageResponseDto;
import org.example.luvbackend.entity.homeimage.HomeImage;
import org.example.luvbackend.exception.homeimage.HomeImageException;
import org.example.luvbackend.exception.homeimage.HomeImageExceptionCode;
import org.example.luvbackend.repository.HomeImageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeImageService {
	private final HomeImageRepository homeImageRepository;
	private final AwsS3Service awsS3Service;

	@Transactional(readOnly = true)
	public PageResponse<HomeImageResponseDto> getHomeImages(int page, int size) {
		return PageResponse.of(
			homeImageRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
				.map(HomeImageResponseDto::new)
		);
	}

	@Transactional
	public HomeImageResponseDto createHomeImage(MultipartFile image) {
		if (image == null || image.isEmpty()) {
			throw new HomeImageException(HomeImageExceptionCode.NO_IMAGE_PROVIDED);
		}
		String imageUrl = awsS3Service.uploadFile(image, S3Directory.HOME_IMAGES);
		return new HomeImageResponseDto(homeImageRepository.save(new HomeImage(imageUrl)));
	}

	@Transactional
	public void deleteHomeImage(String id) {
		HomeImage homeImage = homeImageRepository.findByIdOrElseThrow(id);
		awsS3Service.deleteFile(homeImage.getImageUrl());
		homeImageRepository.delete(homeImage);
	}
}