package org.example.luvbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

	@Value("${spring.cloud.aws.region.static}")
	private String region;

	@Bean
	public S3Client awsS3Client() {
		return S3Client.builder()
			.region(Region.of(region))
			.credentialsProvider(DefaultCredentialsProvider.create()) // 로컬에서 application-local, 운영에선 EC2에 지정된 IAM Role 가져오기
			.build();
	}
}
