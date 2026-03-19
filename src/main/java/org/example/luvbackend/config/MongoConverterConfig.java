package org.example.luvbackend.config;

import java.util.List;

import org.example.luvbackend.entity.album.AlbumType;
import org.example.luvbackend.entity.congregationnews.CongregationNewsType;
import org.example.luvbackend.entity.video.VideoType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConverterConfig {

	// AlbumType 컨버터
	@ReadingConverter
	public static class StringToAlbumTypeConverter implements Converter<String, AlbumType> {
		@Override
		public AlbumType convert(String albumType) {
			return AlbumType.deserialize(albumType);
		}
	}

	@WritingConverter
	public static class AlbumTypeToStringConverter implements Converter<AlbumType, String> {
		@Override
		public String convert(AlbumType albumType) {
			return albumType.getValue();
		}
	}

	// VideoType 컨버터
	@ReadingConverter
	public static class StringToVideoTypeConverter implements Converter<String, VideoType> {
		@Override
		public VideoType convert(String videoType) {
			return VideoType.deserialize(videoType);
		}
	}

	@WritingConverter
	public static class VideoTypeToStringConverter implements Converter<VideoType, String> {
		@Override
		public String convert(VideoType videoType) {
			return videoType.getValue();
		}
	}

	// CongregationNewsType 컨버터
	@ReadingConverter
	public static class StringToCongregationNewsTypeConverter implements Converter<String, CongregationNewsType> {
		@Override
		public CongregationNewsType convert(String type) {
			return CongregationNewsType.deserialize(type);
		}
	}

	@WritingConverter
	public static class CongregationNewsTypeToStringConverter implements Converter<CongregationNewsType, String> {
		@Override
		public String convert(CongregationNewsType type) {
			return type.getValue();
		}
	}

	// TypeScript 에서 isPinned를 "checked" 문자열로 저장한 경우 처리
	@ReadingConverter
	public static class StringToBooleanConverter implements Converter<String, Boolean> {
		@Override
		public Boolean convert(String source) {
			if (source == null) return false;
			return source.equalsIgnoreCase("true") || source.equalsIgnoreCase("checked");
		}
	}

	// Double → Long 컨버터 (Node.js Date.now()가 Double로 저장된 기존 데이터 호환)
	@ReadingConverter
	public static class DoubleToLongConverter implements Converter<Double, Long> {
		@Override
		public Long convert(Double source) {
			return source.longValue();
		}
	}

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(List.of(
			new StringToAlbumTypeConverter(),
			new AlbumTypeToStringConverter(),
			new StringToVideoTypeConverter(),
			new VideoTypeToStringConverter(),
			new StringToCongregationNewsTypeConverter(),
			new CongregationNewsTypeToStringConverter(),
			new DoubleToLongConverter(),
			new StringToBooleanConverter()
		));
	}

	/**
	 * MongoDB 문서에 저장할 때, _class 저장안되게 컨버터 수정
	 */
	@Bean
	@Primary
	public MappingMongoConverter mappingMongoConverter(
		MongoDatabaseFactory factory,
		MongoMappingContext context,
		MongoCustomConversions conversions) {
		MappingMongoConverter converter =
			new MappingMongoConverter(new DefaultDbRefResolver(factory), context);
		converter.setCustomConversions(conversions);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null)); // _class 제거
		return converter;
	}
}
