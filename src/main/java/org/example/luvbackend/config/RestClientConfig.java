package org.example.luvbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
	/**
	 * 싱글톤으로 관리되는 RestClient 등록
	 */
	@Bean
	public RestClient restClient(RestClient.Builder builder) {
		return builder.build();
	}
}
