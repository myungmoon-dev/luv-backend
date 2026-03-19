package org.example.luvbackend.entity.congregationnews;

import org.example.luvbackend.exception.congregationnews.CongregationNewsException;
import org.example.luvbackend.exception.congregationnews.CongregationNewsExceptionCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CongregationNewsType {
	MARRIAGE("marriage"),
	BIRTH("birth"),
	FUNERAL("funeral"),
	OPENING("opening"),
	HOSPITALIZATION("hospitalization"),
	SUNDAY_MANNA("sundayManna"),
	ICE_CREAM("iceCream");

	private final String value;

	public static CongregationNewsType deserialize(String type) {
		for (CongregationNewsType newsType : CongregationNewsType.values()) {
			if (newsType.getValue().equalsIgnoreCase(type.trim())) {
				return newsType;
			}
		}
		throw new CongregationNewsException(CongregationNewsExceptionCode.ILLEGAL_CONGREGATION_NEWS_TYPE);
	}
}
