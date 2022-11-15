package de.neuefische.backend.repository;

import de.neuefische.backend.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
    void deletePhotoByRouteIdAndId(String routeId, String id);
}

