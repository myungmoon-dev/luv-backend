package org.example.luvbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Mongo Auditing 설정 클래스
 * BaseEntity 사용하기 위한 클래스
 */
@EnableMongoAuditing
@Configuration
public class MongoAuditingConfig {
}

