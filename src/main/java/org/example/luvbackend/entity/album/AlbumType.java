package org.example.luvbackend.entity.album;

import org.example.luvbackend.exception.album.AlbumException;
import org.example.luvbackend.exception.album.AlbumExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlbumType {
	MAIN("main"),         // 전체행사
	INFANTS("infants"),   // 영아부
	TODDLERS("toddlers"), // 유치부
	CHILDREN("children"), // 유초등부
	TEENS("teens"),       // 중고등부
	YOUTH("youth"),       // 청년부
	BRIDGE("bridge"),     // 브릿지
	QT("qt"),             // QT
	PANORAMA("panorama"), // 파노라마
	NEW_FAMILY("newFamily"),       // 새가족부
	NEWLY_WEDDINGS("newlyweds"),   // 신혼부부
	GEN_3040("3040");              // 3040

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
