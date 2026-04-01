package org.example.luvbackend.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.luvbackend.dto.aws.S3Directory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.example.luvbackend.exception.aws.AwsS3Exception;
import org.example.luvbackend.exception.aws.AwsS3ExceptionCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {
	private final S3Client awsS3Client;

	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 다건 파일 업로드 후 URL 리스트 반환 메서드
	 */
	public List<String> uploadFiles(List<MultipartFile> files, S3Directory directory) {
		if (files == null || files.isEmpty()) return List.of(); // 리스트가 비어있을 경우, 빈 리스트 반환

		return files.stream()
			.filter(file -> file != null && !file.isEmpty()) // 리스트 객체가 비었는지 필터링
			.map(file -> this.uploadFile(file, directory)) // 순차적으로 단건 업로드
			.toList();
	}

	/**
	 * 다건 파일 업로드 - key 리스트를 직접 받아 업로드 (files[i] → keys[i])
	 */
	public List<String> uploadFiles(List<MultipartFile> files, List<String> keys) {
		if (files == null || files.isEmpty()) return List.of();

		List<String> urls = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			if (file == null || file.isEmpty()) continue;
			urls.add(uploadFile(file, keys.get(i)));
		}
		return urls;
	}

	/**
	 * 단건 파일 업로드 - key를 직접 지정
	 */
	public String uploadFile(MultipartFile file, String key) {
		try {
			awsS3Client.putObject(
				PutObjectRequest.builder()
					.bucket(bucket)
					.key(key)
					.contentType(file.getContentType())
					.contentLength(file.getSize())
					.build(),
				RequestBody.fromInputStream(file.getInputStream(), file.getSize())
			);
		} catch (IOException e) {
			throw new AwsS3Exception(AwsS3ExceptionCode.FILE_UPLOAD_FAILED);
		}
		return awsS3Client.utilities().getUrl(b -> b.bucket(bucket).key(key)).toString();
	}

	/**
	 * 단건 파일 업로드 후 URL 반환 메서드
	 */
	public String uploadFile(MultipartFile file, S3Directory s3Directory) {
		String dirName = s3Directory.getPath(); // ex. albums, bulletins, videos
		String fileName = file.getOriginalFilename(); // ex. image.jpg
		UUID uuid = UUID.randomUUID(); // ex. 1234567890
		String key = dirName + "/" + uuid + "_" + fileName; // ex. albums/1234567890_image.jpg

		try {
			// S3 업로드
			awsS3Client.putObject(
				PutObjectRequest.builder()
					.bucket(bucket)
					.key(key)
					.contentType(file.getContentType())
					.contentLength(file.getSize())
					.build(),
				RequestBody.fromInputStream(file.getInputStream(), file.getSize())
			);
		} catch (IOException e) {
			throw new AwsS3Exception(AwsS3ExceptionCode.FILE_UPLOAD_FAILED);
		}

		// URL 반환
		return awsS3Client.utilities()
			.getUrl(b -> b.bucket(bucket).key(key))
			.toString();
	}

	/**
	 * byte 배열을 지정된 key로 S3에 업로드 후 URL 반환
	 */
	public String uploadBytes(byte[] bytes, String key, String contentType) {
		awsS3Client.putObject(
			PutObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.contentType(contentType)
				.contentLength((long) bytes.length)
				.build(),
			RequestBody.fromBytes(bytes)
		);

		return awsS3Client.utilities()
			.getUrl(b -> b.bucket(bucket).key(key))
			.toString();
	}

	/**
	 * 기존 이미지 URL과 새로 업로드된 URL을 병합하는 메서드
	 * - 둘 다 없으면 빈 리스트 반환 → update()에서 기존 값 유지
	 */
	public List<String> mergeImageUrls(List<String> existingUrls, List<String> uploadedUrls) {
		boolean hasExisting = existingUrls != null && !existingUrls.isEmpty();
		boolean hasUploaded = !uploadedUrls.isEmpty();

		if (!hasExisting && !hasUploaded) return List.of(); // 두 리스트가 모두 비어있을 경우, 빈 리스트 반환

		List<String> merged = new ArrayList<>();
		if (hasExisting) merged.addAll(existingUrls);
		if (hasUploaded) merged.addAll(uploadedUrls);
		return merged;
	}

	/**
	 * 다건 파일 삭제 (best-effort: 개별 실패 시 로그만 남기고 계속 진행)
	 * - 보상 삭제 용도로 사용되므로 예외를 전파하지 않음
	 */
	public void deleteFiles(List<String> fileUrls) {
		if (fileUrls == null || fileUrls.isEmpty()) return;
		fileUrls.forEach(url -> {
			try {
				deleteFile(url);
			} catch (Exception e) {
				log.warn("S3 파일 삭제 실패 (보상 삭제): url={}", url, e);
			}
		});
	}

	/**
	 * 단건 파일 삭제
	 */
	public void deleteFile(String fileUrl) {
		String key = URI.create(fileUrl).getPath().substring(1); // ex. albums/1234567890_image.jpg

		awsS3Client.deleteObject(
			DeleteObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build()
		);
	}
}
