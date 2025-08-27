package org.example.luvbackend.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;

@Getter
public abstract class BaseEntity {
	@CreatedDate
	private LocalDateTime createdAt;

	// @LastModifiedDate
	// private LocalDateTime updatedAt;
}
