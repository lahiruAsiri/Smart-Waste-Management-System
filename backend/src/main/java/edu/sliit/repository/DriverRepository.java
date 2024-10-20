package edu.sliit.repository;

import edu.sliit.document.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends MongoRepository<Driver, String> {
    Optional<Driver> findByDriverId(String driverId);
}
