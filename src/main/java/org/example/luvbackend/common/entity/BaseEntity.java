package org.example.luvbackend.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public abstract class BaseEntity {
	@CreatedDate
	private LocalDateTime createdAt;

	// @LastModifiedDate
	// private LocalDateTime updatedAt;
}
