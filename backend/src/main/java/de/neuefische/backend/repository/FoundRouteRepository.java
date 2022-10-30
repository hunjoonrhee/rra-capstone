package de.neuefische.backend.repository;

import de.neuefische.backend.model.FoundRoutes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundRouteRepository extends MongoRepository<FoundRoutes, String> {
}
