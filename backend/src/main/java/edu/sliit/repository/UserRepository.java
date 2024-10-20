package edu.sliit.repository;

import edu.sliit.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on User documents in MongoDB.
 * Extends MongoRepository, providing built-in methods for common database operations.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Custom method to find users by their username and password.
     * Typically used for authentication purposes.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return List of User objects matching the provided username and password.
     */
    List<User> findByUsernameAndPassword(String username, String password);

    /**
     * Custom method to find users by their username.
     *
     * @param username The username of the user.
     * @return List of User objects matching the provided username.
     */
    List<User> findByUsername(String username);

    /**
     * Custom method to find users by their username.
     *
     * @param email The username of the user.
     * @return List of User objects matching the provided username.
     */
    List<User> findByEmail(String email);
    User findByUserId(String userId);
}
