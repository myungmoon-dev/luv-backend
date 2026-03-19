package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.popup.PopupResponseDto;
import org.example.luvbackend.dto.popup.PopupUploadForm;
import org.example.luvbackend.entity.popup.Popup;
import org.example.luvbackend.exception.popup.PopupException;
import org.example.luvbackend.exception.popup.PopupExceptionCode;
import org.example.luvbackend.repository.PopupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PopupService {
	private static final long MAX_IMAGE_SIZE = 10L * 1024 * 1024;

	private final PopupRepository popupRepository;
	private final AwsS3Service awsS3Service;

	@Transactional(readOnly = true)
	public List<PopupResponseDto> getPopups(Boolean onlyShow) {
		if (Boolean.TRUE.equals(onlyShow)) {
			return popupRepository.findByIsShow(true).stream()
				.map(PopupResponseDto::new)
				.toList();
		}
		return popupRepository.findAll().stream()
			.map(PopupResponseDto::new)
			.toList();
	}

	@Transactional
	public PopupResponseDto createPopup(PopupUploadForm form) {
		MultipartFile image = form.getImage();
		if (image == null || image.isEmpty()) {
			throw new PopupException(PopupExceptionCode.NO_IMAGE_POPUP_FORM);
		}
		if (image.getSize() > MAX_IMAGE_SIZE) {
			throw new PopupException(PopupExceptionCode.IMAGE_SIZE_OVER_FORM);
		}
		String imageUrl = awsS3Service.uploadFile(image, S3Directory.POPUPS);
		Popup popup = Popup.builder()
			.title(form.getTitle())
			.isShow(true)
			.imageUrl(imageUrl)
			.build();
		return new PopupResponseDto(popupRepository.save(popup));
	}

	@Transactional
	public PopupResponseDto toggleShow(String id) {
		Popup popup = popupRepository.findByIdOrElseThrow(id);
		popup.toggleShow();
		return new PopupResponseDto(popupRepository.save(popup));
	}

	@Transactional
	public void deletePopup(String id) {
		Popup fromDB = popupRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFile(fromDB.getImageUrl());
		popupRepository.delete(fromDB);
	}
}
