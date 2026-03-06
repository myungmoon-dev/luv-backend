package org.example.luvbackend.dto.cloudflare;

import lombok.Builder;
import lombok.Getter;

/**
 * Direct Upload URL DTO 이다.
 * 1. result.get("id"), result.get("uploadURL")보다 가독성
 * direct upload API가 응답한 의미가 명확해짐
 * 타입안정성
 * 유지보수성 (추후 응답 스펙을 확장했을 때도 쉽게 추가할 수 있음)
 */
@Getter
public class DirectUploadUrl {
	private final String id;
	private final String url;

	@Builder
	public DirectUploadUrl(String id, String url) {
		this.id = id;
		this.url = url;
	}
}
