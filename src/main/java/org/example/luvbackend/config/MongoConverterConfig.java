package org.example.luvbackend.config;

import java.util.List;

import org.example.luvbackend.entity.album.AlbumType;
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
	@ReadingConverter
	public static class stringToAlbumTypeConverter implements Converter<String, AlbumType> {
		@Override
		public AlbumType convert(String albumType){
			return AlbumType.deserialize(albumType);
		}
	}
	@WritingConverter
	public static class albumTypeToStringConverter implements Converter<AlbumType, String> {
		@Override
		public String convert(AlbumType albumType) {
			return albumType.getValue(); // enum 내부 커스텀 값
		}
	}

	/**
	 * 커스컴 컨버터 Bean
	 */
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(List.of(
			new stringToAlbumTypeConverter(),
			new albumTypeToStringConverter()
		));
	}

	/**
	 * MongoDB 문서에 저장할 때, _class 저장안되게 컨버터 수정
	 *
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
