package org.example.luvbackend.service;

import java.util.List;

import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.dto.missionnews.MissionNewsCreateForm;
import org.example.luvbackend.dto.missionnews.MissionNewsResponseDto;
import org.example.luvbackend.dto.missionnews.MissionNewsUpdateForm;
import org.example.luvbackend.entity.missionnews.MissionNews;
import org.example.luvbackend.repository.MissionNewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MissionNewsService {
	private final MissionNewsRepository missionNewsRepository;
	private final AwsS3Service awsS3Service;

	/**
	 * 다건 지역별 선교 소식 조회
	 */
	@Transactional(readOnly = true)
	public Page<MissionNewsResponseDto> getMissionNewsList(String location, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		if (location != null && !location.isBlank()) {
			return missionNewsRepository.findByLocationOrderByCreatedAtDesc(location, pageable)
				.map(MissionNewsResponseDto::from);
		}
		return missionNewsRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(MissionNewsResponseDto::from);
	}

	/**
	 * 단건 선교 소식 조회
	 */
	@Transactional(readOnly = true)
	public MissionNewsResponseDto getMissionNews(String id) {
		return MissionNewsResponseDto.from(missionNewsRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 선교 소식 생성
	 */
	@Transactional
	public MissionNewsResponseDto createMissionNews(MissionNewsCreateForm form) {
		List<String> imageUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.MISSION_NEWS);
		return MissionNewsResponseDto.from(missionNewsRepository.save(MissionNews.of(form, imageUrls)));
	}

	/**
	 * 단건 선교 소식 수정
	 */
	@Transactional
	public MissionNewsResponseDto updateMissionNews(String id, MissionNewsUpdateForm form) {
		MissionNews missionNews = missionNewsRepository.findByIdOrElseThrow(id);

		List<String> uploadedUrls = awsS3Service.uploadFiles(form.getImages(), S3Directory.MISSION_NEWS);
		List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), uploadedUrls);

		String date = form.getDate() != null ? form.getDate().toString() : null;
		missionNews.update(form.getTitle(), form.getContent(), form.getUserName(), date, form.getLocation(), mergedImageUrls);
		return MissionNewsResponseDto.from(missionNewsRepository.save(missionNews));
	}

	/**
	 * 단건 선교 소식 삭제
	 */
	@Transactional
	public void deleteMissionNews(String id) {
		MissionNews fromDB = missionNewsRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls()); // 이미지 삭제
		missionNewsRepository.delete(fromDB); // DB 삭제
	}

}
