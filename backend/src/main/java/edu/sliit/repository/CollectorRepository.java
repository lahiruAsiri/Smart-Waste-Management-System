package edu.sliit.repository;

import edu.sliit.document.Collector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Collector documents in MongoDB.
 * Extends MongoRepository, which provides built-in methods for common database operations.
 */
@Repository
public interface CollectorRepository extends MongoRepository<Collector, String> {

    /**
     * Custom method to find all collectors by a specific user ID.
     *
     * @param userId The ID of the user whose collectors are being retrieved.
     * @return List of Collector objects associated with the given userId.
     */
    List<Collector> findAllByUserId(String userId);
    List<Collector> findAllByBinId(String binId);

}
