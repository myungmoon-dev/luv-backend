package org.example.luvbackend.repository;

import org.example.luvbackend.entity.album.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
}
