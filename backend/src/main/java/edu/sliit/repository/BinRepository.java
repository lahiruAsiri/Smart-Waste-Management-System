package edu.sliit.repository;

import edu.sliit.document.Bin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Bin documents in MongoDB.
 * Extends MongoRepository, which provides built-in methods for common database operations.
 */
@Repository
public interface BinRepository extends MongoRepository<Bin, String> {

    /**
     * Custom method to find all bin documents by a specific user ID.
     *
     * @param userId The ID of the user whose bins are being retrieved.
     * @return List of Bin objects associated with the given userId.
     */
    List<Bin> findAllByUserId(String userId);

}
