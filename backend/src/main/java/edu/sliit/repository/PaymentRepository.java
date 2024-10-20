package edu.sliit.repository;

import edu.sliit.document.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Payment documents in MongoDB.
 * Extends MongoRepository, providing built-in methods for common database operations.
 */
@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    /**
     * Custom method to find all payments by a specific user ID.
     *
     * @param userId The ID of the user whose payment records are being retrieved.
     * @return List of Payment objects associated with the given userId.
     */
    List<Payment> findByUserId(String userId);

}
