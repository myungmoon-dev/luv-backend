package org.example.luvbackend.service;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.dto.video.VideoCreateForm;
import org.example.luvbackend.dto.video.VideoResponseDto;
import org.example.luvbackend.dto.video.VideoUpdateForm;
import org.example.luvbackend.entity.video.Video;
import org.example.luvbackend.entity.video.VideoType;
import org.example.luvbackend.repository.VideoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoService {
	private final VideoRepository videoRepository;

	/**
	 * 다건 타입별 영상 조회
	 */
	@Transactional(readOnly = true)
	public PageResponse<VideoResponseDto> getVideos(String type, int page, int size) {
		if (type != null && !type.isBlank()) {
			VideoType videoType = VideoType.deserialize(type);
			return PageResponse.of(videoRepository.findByTypeOrderByCreatedAtDesc(videoType, PageRequest.of(page, size))
				.map(VideoResponseDto::from));
		}
		return PageResponse.of(videoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
			.map(VideoResponseDto::from));
	}

	/**
	 * 단건 영상 생성
	 */
	@Transactional
	public VideoResponseDto createVideo(VideoCreateForm form) {
		return VideoResponseDto.from(videoRepository.save(Video.from(form)));
	}

	/**
	 * 단건 영상 수정
	 */
	@Transactional
	public VideoResponseDto updateVideo(String id, VideoUpdateForm form) {
		Video video = videoRepository.findByIdOrElseThrow(id);
		// type이 전달된 경우에만 역직렬화, null이면 기존 값 유지
		VideoType videoType = form.getType() != null ? VideoType.deserialize(form.getType()) : null;
		video.update(form.getUrl(), form.getTitle(), form.getDate(), form.getMainText(), form.getPreacher(), videoType);
		return VideoResponseDto.from(videoRepository.save(video));
	}

	/**
	 * 단건 영상 삭제
	 */
	@Transactional
	public void deleteVideo(String id) {
		videoRepository.delete(videoRepository.findByIdOrElseThrow(id));
	}
}
