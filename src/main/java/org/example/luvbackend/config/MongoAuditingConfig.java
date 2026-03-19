package org.example.luvbackend.config;

import java.time.Instant;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Mongo Auditing 설정 클래스
 * - DateTimeProvider를 Instant 반환으로 등록하여 Long 타입 @CreatedDate/@LastModifiedDate 지원
 */
@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "epochMilliDateTimeProvider")
public class MongoAuditingConfig {

	@Bean
	public DateTimeProvider epochMilliDateTimeProvider() {
		return () -> Optional.of(Instant.now());
	}
}
