package org.example.luvbackend.config;

import java.util.List;

import org.example.luvbackend.entity.album.AlbumType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConverterConfig {
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(List.of(
			// DB -> Enum
			new Converter<String, AlbumType>() {
				@Override
				public AlbumType convert(String albumType) {
					return AlbumType.deserialize(albumType);
				}
			},
			// Enum -> DB
			new Converter<AlbumType, String>() {
				@Override
				public String convert(AlbumType albumType) {
					return albumType.getValue();
				}
			}
		));
	}
}
