package org.example.luvbackend.common.util;

/**
 * S3 key/파일명 생성에 사용하는 문자열 유틸
 */
public final class FileUtils {

	private FileUtils() {
	}

	/**
	 * 제목 등을 파일명에 안전하게 사용하도록 변환하는 함수
	 * - 한글/영문/숫자는 유지, 그 외(공백·특수문자·'/')는 '_'로 치환 후 양끝 '_' 제거
	 */
	public static String sanitize(String title) {
		if (title == null) return "";
		return title.replaceAll("[^\\p{L}\\p{N}]+", "_")
			.replaceAll("^_+|_+$", "");
	}

	private static final String DEFAULT_EXTENSION = ".webp";

	/**
	 * 파일명에서 확장자를 점(.) 포함 + 소문자로 추출
	 * - 확장자가 없으면 기본값 ".webp" 반환
	 * ex. "image.JPG" → ".jpg", 확장자 없음 → ".webp"
	 */
	public static String extractExtension(String filename) {
		if (filename == null || !filename.contains(".")) return DEFAULT_EXTENSION;
		return filename.substring(filename.lastIndexOf('.')).toLowerCase();
	}
}
