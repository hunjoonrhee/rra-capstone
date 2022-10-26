package de.neuefische.backend.repository;

import de.neuefische.backend.model.LocationReturn;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<LocationReturn, String> {
}
