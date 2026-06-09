package org.example.luvbackend.service;

import org.example.luvbackend.dto.pastorprofileimage.PastorProfileImageForm;
import org.example.luvbackend.dto.pastorprofileimage.PastorProfileImageResponseDto;
import org.example.luvbackend.entity.pastorprofileimage.PastorProfileImage;
import org.example.luvbackend.exception.pastorprofileimage.PastorProfileImageException;
import org.example.luvbackend.exception.pastorprofileimage.PastorProfileImageExceptionCode;
import org.example.luvbackend.repository.PastorProfileImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PastorProfileImageService {
	private static final String TOP_KEY = "leadership/pastor/senior/images/top.jpg";
	private static final String BOTTOM_KEY = "leadership/pastor/senior/images/bottom.jpg";

	private final PastorProfileImageRepository pastorProfileImageRepository;
	private final AwsS3Service awsS3Service;

	@Transactional(readOnly = true)
	public PastorProfileImageResponseDto getPastorProfileImage() {
		return new PastorProfileImageResponseDto(pastorProfileImageRepository.findProfileImageOrElseThrow());
	}

	@Transactional
	public PastorProfileImageResponseDto updatePastorProfileImage(PastorProfileImageForm form) {
		MultipartFile topImage = form.getTopImage();
		MultipartFile bottomImage = form.getBottomImage();

		boolean hasTop = topImage != null && !topImage.isEmpty();
		boolean hasBottom = bottomImage != null && !bottomImage.isEmpty();

		if (!hasTop && !hasBottom) {
			throw new PastorProfileImageException(PastorProfileImageExceptionCode.NO_IMAGE_PROVIDED);
		}

		PastorProfileImage profile = pastorProfileImageRepository.findById("pastor-profile-image")
			.orElse(null);

		String topUrl = profile != null ? profile.getTopImageUrl() : null;
		String bottomUrl = profile != null ? profile.getBottomImageUrl() : null;

		if (hasTop) topUrl = awsS3Service.uploadFile(topImage, TOP_KEY);
		if (hasBottom) bottomUrl = awsS3Service.uploadFile(bottomImage, BOTTOM_KEY);

		if (profile == null) {
			profile = new PastorProfileImage(topUrl, bottomUrl);
		} else {
			if (hasTop) profile.updateTopImageUrl(topUrl);
			if (hasBottom) profile.updateBottomImageUrl(bottomUrl);
		}

		return new PastorProfileImageResponseDto(pastorProfileImageRepository.save(profile));
	}
}