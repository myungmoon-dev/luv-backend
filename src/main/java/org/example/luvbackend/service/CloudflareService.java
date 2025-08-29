package org.example.luvbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.example.luvbackend.dto.cloudflare.DirectUploadUrl;
import org.example.luvbackend.exception.album.AlbumException;
import org.example.luvbackend.exception.album.AlbumExceptionCode;
import org.example.luvbackend.exception.cloudflare.CloudflareException;
import org.example.luvbackend.exception.cloudflare.CloudflareExceptionCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudflareService {

	private static final long MAX_IMAGE_SIZE = 10L * 1024 * 1024; // 10MB

	@Value("${cloudflare.images.account-id}")
	private String accountId;

	@Value("${cloudflare.images.api-key}")
	private String apiKey;

	private final ObjectMapper objectMapper;
	private final RestClient restClient;

	public List<String> uploadImages(List<MultipartFile> images) {
		if (images == null || images.isEmpty()) {
			throw new AlbumException(AlbumExceptionCode.NO_IMAGE_ALBUM_FORM);
		}

		List<String> urls = new ArrayList<>();
		for (MultipartFile image : images) {
			// 1) image 객체가 유효한지 검증
			verifyImage(image);

			// 2) direct upload URL 발급 (multipart/form-data)
			DirectUploadUrl directUploadUrl = createDirectUploadUrl();

			// 3) 발급받은 URL로 실제 파일 업로드
			String s = uploadImage(directUploadUrl.getUrl(), image);

			// 4) 업로드한 이미지 배열에 추가
			urls.add(s);
		}
		return urls;
	}

	/**
	 * image 객체가 비어있지 않은지, 최대 이미지크기를 넘지 않았는지 검증하는 메서드
	 * @param image 이미지 객체
	 */
	private void verifyImage(MultipartFile image) {
		if (image == null || image.isEmpty()) {
			throw new AlbumException(AlbumExceptionCode.NO_IMAGE_ALBUM_FORM);
		}
		if (image.getSize() > MAX_IMAGE_SIZE) {
			throw new AlbumException(AlbumExceptionCode.IMAGE_SIZE_OVER_ALBUM_FORM);
		}
	}

	/**
	 * Cloudflare 로부터 direct upload URL 발급받는 메서드
	 * @return 발급받은 ID, URL을 객체로 반환
	 */
	private DirectUploadUrl createDirectUploadUrl() {
		String url = "https://api.cloudflare.com/client/v4/accounts/" + accountId + "/images/v2/direct_upload";

		// 공식문서에서 multipart/form-data 쓰라고 함 (--form 옵션을 사용)
		MultipartBodyBuilder mb = new MultipartBodyBuilder();
		MultiValueMap<String, HttpEntity<?>> body = mb.build();

		String response = restClient.post()
			.uri(url)
			.headers(headers -> {
				headers.setBearerAuth(apiKey);
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			})
			.body(body)
			.retrieve()
			.onStatus(HttpStatusCode::isError, (req, res) -> {
				throw new CloudflareException(CloudflareExceptionCode.DIRECT_UPLOAD_REQUEST_ERROR);
			})
			.body(String.class); // 응답을 String 객체로 반환

		try {
			JsonNode root = objectMapper.readTree(response); // objectMapper로 JSON 변환
			if (!root.path("success").asBoolean(false)) {
				throw new CloudflareException(CloudflareExceptionCode.DIRECT_UPLOAD_RESPONSE_ERROR);
			}

			// 데이터 추출
			JsonNode result = root.path("result");
			String uploadId   = result.path("id").asText(null);
			String uploadUrl = result.path("uploadURL").asText(null);
			if (uploadId == null || uploadUrl == null)  {
				throw new CloudflareException(CloudflareExceptionCode.DIRECT_UPLOAD_ID_OR_URL_NOT_FOUND);
			}
			// DirectUploadUrl 객체로 반환
			return DirectUploadUrl.builder()
				.id(uploadId)
				.url(uploadUrl)
				.build();
		} catch (IOException e) {
			throw new CloudflareException(CloudflareExceptionCode.DIRECT_UPLOAD_JSON_PARSING_ERROR);
		}
	}

	/**
	 * Cloudflare에 이미지를 업로드하는 메서드
	 * @param uploadUrl 발급받은 Direct Upload URL
	 * @param file 이미지 파일
	 * @return 업로드 이미지 경로를 반환
	 */
	private String uploadImage(String uploadUrl, MultipartFile file) {
		// 공식문서에서 multipart/form-data 쓰라고 함
		MultipartBodyBuilder mb = new MultipartBodyBuilder();

		// Image file 설정
		var mbPart = mb.part("file", file.getResource());
		if (file.getOriginalFilename() != null) mbPart.filename(file.getOriginalFilename());
		if (file.getContentType() != null) mbPart.contentType(MediaType.parseMediaType(file.getContentType()));

		MultiValueMap<String, HttpEntity<?>> body = mb.build();

		String response = restClient.post()
			.uri(uploadUrl)
			.headers(headers -> headers.setContentType(MediaType.MULTIPART_FORM_DATA))
			.body(body)
			.retrieve()
			.onStatus(HttpStatusCode::isError, (req, res) -> {
				throw new CloudflareException(CloudflareExceptionCode.CLOUDFLARE_UPLOAD_FAILED);
			})
			.body(String.class);// 응답을 String 객체로 반환

		try {
			JsonNode root = objectMapper.readTree(response); // objectMapper로 JSON 변환
			if (!root.path("success").asBoolean(false)) {
				throw new CloudflareException(CloudflareExceptionCode.DIRECT_UPLOAD_RESPONSE_ERROR);
			}

			// 데이터 추출
			JsonNode variants = root.path("result").path("variants");
			if (!variants.isArray() || variants.isEmpty()) {
				throw new CloudflareException(CloudflareExceptionCode.CLOUDFLARE_VARIANTS_NOT_FOUND);
			}
			// 업로드된 이미지 경로 반환
			return variants.get(0).asText();
		} catch (IOException e) {
			throw new CloudflareException(CloudflareExceptionCode.DIRECT_UPLOAD_JSON_PARSING_ERROR);
		}
	}
}
