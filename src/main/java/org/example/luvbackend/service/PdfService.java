package org.example.luvbackend.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.example.luvbackend.dto.aws.S3Directory;
import org.example.luvbackend.exception.bulletin.BulletinException;
import org.example.luvbackend.exception.bulletin.BulletinExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfService {
	private final AwsS3Service awsS3Service;

	/**
	 * PDF 각 페이지를 이미지로 변환 후 S3 업로드
	 * 네이밍: bulletins/yyyyMMdd/01.jpg, _02.jpg ...
	 */
	public List<String> convertAndUpload(MultipartFile file, LocalDate date) {
		log.info("PDF 변환 요청 - filename: {}, date: {}", file.getOriginalFilename(), date);

		try (PDDocument pdf = Loader.loadPDF(file.getBytes())) {
			PDFRenderer renderer = new PDFRenderer(pdf);
			int pageCount = pdf.getNumberOfPages();
			List<String> imageUrls = new ArrayList<>();

			for (int i = 0; i < pageCount; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 150);

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", byteArrayOutputStream);
				byte[] imageBytes = byteArrayOutputStream.toByteArray();

				String key = generateBulletinPath(date, i + 1);
				String url = awsS3Service.uploadBytes(imageBytes, key, "image/jpeg");
				log.info("S3 업로드 완료 - page: {}/{}, key: {}", i + 1, pageCount, key);
				imageUrls.add(url);
			}

			return imageUrls;
		} catch (IOException e) {
			log.error("PDF 변환 실패 - filename: {}, date: {}", file.getOriginalFilename(), date);
			throw new BulletinException(BulletinExceptionCode.PDF_CONVERT_FAILED);
		}
	}

	/**
	 * 주보날짜와 INDEX로 S3 Path 생성
	 */
	private String generateBulletinPath(LocalDate date, int index) {
		String pageNumber = String.format("%02d", index); // 페이지 번호 2자리 포맷 (1 → "01")
		String dateFormatted = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return S3Directory.BULLETINS.getPath() + "/" + dateFormatted + "/" + pageNumber + ".jpg";
	}
}