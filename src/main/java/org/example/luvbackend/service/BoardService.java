package org.example.luvbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.luvbackend.common.dto.PageResponse;
import org.example.luvbackend.common.util.FileUtils;
import org.example.luvbackend.dto.board.BoardResponseDto;
import org.example.luvbackend.dto.board.BoardUpdateForm;
import org.example.luvbackend.dto.board.BoardUploadForm;
import org.example.luvbackend.entity.board.Board;
import org.example.luvbackend.repository.BoardRepository;
import org.springframework.data.domain.Page;
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
public class BoardService {
	private static final String IMAGE_DIR = "boards/images"; // boards/images/{type}/{title}.jpg
	private static final String FILE_DIR = "boards/files"; // boards/files/{type}/{filename}

	private final BoardRepository boardRepository;
	private final AwsS3Service awsS3Service;

	/**
	 * 다건 페이징 게시글 조회 (type 필터 optional)
	 */
	@Transactional(readOnly = true)
	public PageResponse<BoardResponseDto> getBoards(String type, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Board> boards = (type != null && !type.isBlank())
			? boardRepository.findByTypeOrderByCreatedAtDesc(type, pageable)
			: boardRepository.findAllByOrderByCreatedAtDesc(pageable);
		return PageResponse.of(boards.map(BoardResponseDto::from));
	}

	/**
	 * 단건 게시글 조회
	 */
	@Transactional(readOnly = true)
	public BoardResponseDto getBoard(String id) {
		return BoardResponseDto.from(boardRepository.findByIdOrElseThrow(id));
	}

	/**
	 * 단건 게시글 생성
	 */
	@Transactional
	public BoardResponseDto createBoard(BoardUploadForm form) {
		log.info("게시글 생성 - type: {}, title: {}, writer: {}", form.getType(), form.getTitle(), form.getWriter());

		List<String> imageUrls = awsS3Service.uploadFiles(
			form.getImages(), buildImageKeys(form.getType(), form.getTitle(), form.getImages()));
		List<String> fileUrls = awsS3Service.uploadFiles(
			form.getFiles(), buildFileKeys(form.getType(), form.getFiles()));

		return BoardResponseDto.from(boardRepository.save(Board.of(form, imageUrls, fileUrls)));
	}

	/**
	 * 단건 게시글 수정 (PATCH 방식 - null 필드는 기존 값 유지)
	 */
	@Transactional
	public BoardResponseDto updateBoard(String id, BoardUpdateForm form) {
		Board board = boardRepository.findByIdOrElseThrow(id);

		// S3 key 생성에 사용할 값 - 변경 값이 없으면 기존 값 사용
		String type = form.getType() != null ? form.getType() : board.getType();
		String title = form.getTitle() != null ? form.getTitle() : board.getTitle();

		// 새 이미지 업로드 후 (유지할 기존 이미지 + 새 이미지) 병합
		List<String> newImageUrls = awsS3Service.uploadFiles(
			form.getNewImages(), buildImageKeys(type, title, form.getNewImages()));
		List<String> mergedImageUrls = awsS3Service.mergeImageUrls(form.getExistingImageUrls(), newImageUrls);

		// 새 파일 업로드 후 (유지할 기존 파일 + 새 파일) 병합
		List<String> newFileUrls = awsS3Service.uploadFiles(
			form.getNewFiles(), buildFileKeys(type, form.getNewFiles()));
		List<String> mergedFileUrls = awsS3Service.mergeImageUrls(form.getExistingFileUrls(), newFileUrls);

		board.update(form.getType(), form.getTitle(), form.getWriter(), form.getContent(),
			mergedImageUrls, mergedFileUrls);
		return BoardResponseDto.from(boardRepository.save(board));
	}

	/**
	 * 단건 게시글 삭제
	 */
	@Transactional
	public void deleteBoard(String id) {
		log.info("게시글 삭제 - id: {}", id);
		Board fromDB = boardRepository.findByIdOrElseThrow(id);

		awsS3Service.deleteFiles(fromDB.getImageUrls()); // 이미지 삭제
		awsS3Service.deleteFiles(fromDB.getFileUrls()); // 첨부파일 삭제
		boardRepository.delete(fromDB); // DB 삭제
	}

	/**
	 * 이미지 S3 key 생성 - boards/images/{type}/{title}_{uuid}.{ext}
	 * (한 게시글에 여러 이미지가 있어도 충돌하지 않도록 짧은 uuid 부여)
	 */
	private List<String> buildImageKeys(String type, String title, List<MultipartFile> images) {
		if (images == null || images.isEmpty()) return List.of();

		String dir = IMAGE_DIR + "/" + FileUtils.sanitize(type);
		String safeTitle = FileUtils.sanitize(title);

		List<String> keys = new ArrayList<>();
		for (MultipartFile image : images) {
			String ext = FileUtils.extractExtension(image.getOriginalFilename());
			keys.add(dir + "/" + safeTitle + "_" + shortUuid() + ext);
		}
		return keys;
	}

	/**
	 * 첨부파일 S3 key 생성 - boards/files/{type}/{uuid}_{원본파일명}
	 * (원본 파일명을 유지하되 uuid 접두어로 덮어쓰기 방지)
	 */
	private List<String> buildFileKeys(String type, List<MultipartFile> files) {
		if (files == null || files.isEmpty()) return List.of();

		String dir = FILE_DIR + "/" + FileUtils.sanitize(type);

		List<String> keys = new ArrayList<>();
		for (MultipartFile file : files) {
			keys.add(dir + "/" + shortUuid() + "_" + file.getOriginalFilename());
		}
		return keys;
	}

	private String shortUuid() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
}
