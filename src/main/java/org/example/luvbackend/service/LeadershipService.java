package org.example.luvbackend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.common.util.FileUtils;
import org.example.luvbackend.dto.leadership.LeadershipForm;
import org.example.luvbackend.dto.leadership.LeadershipResponseDto;
import org.example.luvbackend.entity.leadership.Leadership;
import org.example.luvbackend.exception.leadership.LeadershipException;
import org.example.luvbackend.exception.leadership.LeadershipExceptionCode;
import org.example.luvbackend.repository.LeadershipRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadershipService {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	private final LeadershipRepository leadershipRepository;
	private final AwsS3Service awsS3Service;

	@Transactional(readOnly = true)
	public PageResponse<LeadershipResponseDto> getLeaderships(String tabType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		if (tabType != null && !tabType.isBlank()) {
			return PageResponse.of(
				leadershipRepository.findByTabTypeOrderByOrderAsc(tabType, pageable)
					.map(LeadershipResponseDto::from)
			);
		}
		return PageResponse.of(
			leadershipRepository.findAllByOrderByOrderAsc(pageable)
				.map(LeadershipResponseDto::from)
		);
	}

	@Transactional
	public LeadershipResponseDto createLeadership(LeadershipForm form) {
		Integer order = parseOrder(form.getOrder());

		if (order != null && leadershipRepository.existsByTabTypeAndOrder(form.getTabType(), order)) {
			throw new LeadershipException(LeadershipExceptionCode.DUPLICATE_ORDER);
		}

		String imageUrl = awsS3Service.uploadFile(form.getImage(), buildS3Key(form.getTabType(), form.getName(), form.getImage()));
		Leadership leadership = Leadership.builder()
			.tabType(form.getTabType())
			.name(form.getName())
			.imageUrl(imageUrl)
			.position(form.getPosition())
			.officerType(form.getOfficerType())
			.greeting(form.getGreeting())
			.description(form.getDescription())
			.order(order)
			.build();
		return LeadershipResponseDto.from(leadershipRepository.save(leadership));
	}

	@Transactional
	public LeadershipResponseDto updateLeadership(String id, LeadershipForm form) {
		Leadership leadership = leadershipRepository.findByIdOrElseThrow(id);
		Integer order = parseOrder(form.getOrder());

		if (order != null && leadershipRepository.existsByTabTypeAndOrderAndIdNot(leadership.getTabType(), order, id)) {
			throw new LeadershipException(LeadershipExceptionCode.DUPLICATE_ORDER);
		}

		String imageUrl = null;
		MultipartFile image = form.getImage();
		if (image != null && !image.isEmpty()) {
			awsS3Service.deleteFiles(List.of(leadership.getImageUrl()));
			String name = form.getName() != null ? form.getName() : leadership.getName();
			imageUrl = awsS3Service.uploadFile(image, buildS3Key(leadership.getTabType(), name, image));
		}

		leadership.update(form.getName(), imageUrl, form.getPosition(),
			form.getOfficerType(), form.getGreeting(), form.getDescription(), order);
		return LeadershipResponseDto.from(leadershipRepository.save(leadership));
	}

	@Transactional
	public void deleteLeadership(String id) {
		Leadership leadership = leadershipRepository.findByIdOrElseThrow(id);
		awsS3Service.deleteFiles(List.of(leadership.getImageUrl()));
		leadershipRepository.delete(leadership);
	}

	private Integer parseOrder(String order) {
		if (order == null || order.isBlank()) return null;
		try {
			return Integer.parseInt(order.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String buildS3Key(String tabType, String name, MultipartFile file) {
		String now = LocalDateTime.now().format(FORMATTER);
		String ext = FileUtils.extractExtension(file.getOriginalFilename());
		String safeName = (name != null) ? name : "unknown";
		return "leadership/" + tabType + "/" + safeName + "_" + now + ext;
	}
}
