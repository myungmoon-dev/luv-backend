package org.example.luvbackend.common.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;

@Getter
public abstract class BaseEntity {
	@CreatedDate
	private Long createdAt;

	@LastModifiedDate
	private Long updatedAt;
}
