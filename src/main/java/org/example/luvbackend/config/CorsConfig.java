package org.example.luvbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Value("${FRONTEND_URL}")
	private String frontUrl;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins(
				"http://localhost:3000",
				"http://localhost:3001",
				"https://luv-renewal.netlify.app",
				"https://myungmoon.or.kr",
				"https://admin.myungmoon.or.kr",
				frontUrl
			)
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true);
	}
}