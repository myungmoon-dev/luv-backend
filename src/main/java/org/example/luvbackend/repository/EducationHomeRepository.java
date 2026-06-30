package org.example.luvbackend.repository;

import org.example.luvbackend.entity.education.EducationHome;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationHomeRepository extends MongoRepository<EducationHome, String> {
}
