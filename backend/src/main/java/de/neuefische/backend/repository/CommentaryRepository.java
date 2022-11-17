package de.neuefische.backend.repository;

import de.neuefische.backend.model.Commentary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentaryRepository extends MongoRepository<Commentary, String> {
}
