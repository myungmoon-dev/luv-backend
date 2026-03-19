package org.example.luvbackend.repository;

import org.example.luvbackend.entity.album.Album;
import org.example.luvbackend.entity.album.AlbumType;
import org.example.luvbackend.exception.album.AlbumException;
import org.example.luvbackend.exception.album.AlbumExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
	Page<Album> findByTypeOrderByCreatedAtDesc(AlbumType type, Pageable pageable);

	Page<Album> findAllByOrderByCreatedAtDesc(Pageable pageable);

	default Album findByIdOrElseThrow(String albumId) {
		return findById(albumId)
			.orElseThrow(() ->
				new AlbumException(AlbumExceptionCode.NOT_FOUND_ALBUM_OBJECT));
	}
}
