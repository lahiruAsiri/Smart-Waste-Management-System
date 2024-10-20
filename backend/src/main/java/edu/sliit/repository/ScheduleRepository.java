package edu.sliit.repository;

import edu.sliit.document.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository interface for managing Schedule documents in MongoDB
@Repository // Indicates that this interface is a Spring Data repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    // Method to find a Schedule by its scheduleId
    Optional<Schedule> findByScheduleId(String scheduleId); // Returns an Optional containing the Schedule if found
}
