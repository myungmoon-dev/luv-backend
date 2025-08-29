package org.example.luvbackend.entity.album;

import org.example.luvbackend.exception.album.AlbumException;
import org.example.luvbackend.exception.album.AlbumExceptionCode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
public enum AlbumType {
	ALL("all"),
	MAIN("main"),
	INFANTS("infants"),
	TODDLERS("toddlers"),
	ELEMENTARY("elementary"),
	MIDDLE("middle"),
	HIGH("high"),
	YOUTH("youth"),
	QT("qt"),
	PANORAMA("panorama"),
	NEW_FAMILY("newFamily"),
	NEWLY_WEDDINGS("newlyweds"),
	GEN_3040("3040");

	private final String value;

	public static AlbumType deserialize(String type){
		for(AlbumType albumType : AlbumType.values()){
			if(albumType.getValue().equalsIgnoreCase(type.trim())){
				return albumType;
			}
		}
		throw new AlbumException(AlbumExceptionCode.ILLEGAL_ALBUM_TYPE);
	}
}
