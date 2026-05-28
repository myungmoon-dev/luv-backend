package org.example.luvbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

	@Value("${spring.cloud.aws.region.static}")
	private String region;

	/**
	 * [local]
	 * StaticCredentialsProvider: 체인을 탐색하지 않고, 지정한 키를 그대로 사용
	 * -Dspring.profiles.active=local 및 Variable 입력 필요
	 */
	@Bean
	@Profile("local")
	public S3Client localS3Client(
		@Value("${spring.cloud.aws.credentials.access-key}") String accessKey,
		@Value("${spring.cloud.aws.credentials.secret-key}") String secretKey
	) {
		return S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
			.build();
	}

	/**
	 * [prod] AWS S3 Client: EC2 IAM Role 설정해야함
	 * InstanceProfileCredentialsProvider: EC2 설정된 IAM Role을 읽음
	 */
	@Bean
	@Profile("prod")
	public S3Client prodS3Client() {
		return S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(InstanceProfileCredentialsProvider.create())
			.build();
	}
}
