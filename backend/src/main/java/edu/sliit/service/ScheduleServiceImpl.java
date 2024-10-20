package edu.sliit.service;

import edu.sliit.document.Schedule;
import edu.sliit.dto.ScheduleDto;
import edu.sliit.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Implementation of the ScheduleService interface
@Service // Marks this class as a service component in the Spring context
@RequiredArgsConstructor // Generates a constructor with required arguments (final fields)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository; // Repository for Schedule documents

    @Override
    public ResponseEntity<String> addSchedule(ScheduleDto dto) {
        // Create a new Schedule object from the DTO
        Schedule newSchedule = new Schedule(null, dto.getScheduleId(), dto.getSmartBins(), dto.getDriverId(), dto.getTime(), dto.getRoute());
        scheduleRepository.save(newSchedule); // Save the new schedule to the repository
        return new ResponseEntity<>("Schedule added successfully", HttpStatus.CREATED); // Return a response indicating success
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        // Retrieve all schedules, convert them to DTOs, and return the list
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleDto(schedule.getScheduleId(), schedule.getSmartBins(), schedule.getDriverId(), schedule.getTime(), schedule.getRoute()))
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDto getScheduleById(String scheduleId) {
        // Find a schedule by its ID and convert it to a DTO if present
        Optional<Schedule> schedule = scheduleRepository.findByScheduleId(scheduleId);
        return schedule.map(s -> new ScheduleDto(s.getScheduleId(), s.getSmartBins(), s.getDriverId(), s.getTime(), s.getRoute()))
                .orElse(null); // Return null if the schedule is not found
    }

    @Override
    public ResponseEntity<String> updateSchedule(ScheduleDto dto) {
        // Check if the schedule exists before updating
        Optional<Schedule> existingSchedule = scheduleRepository.findByScheduleId(dto.getScheduleId());
        if (existingSchedule.isPresent()) {
            // Update the existing schedule's fields with the new data
            Schedule updatedSchedule = existingSchedule.get();
            updatedSchedule.setSmartBins(dto.getSmartBins());
            updatedSchedule.setDriverId(dto.getDriverId());
            updatedSchedule.setTime(dto.getTime());
            updatedSchedule.setRoute(dto.getRoute());
            scheduleRepository.save(updatedSchedule); // Save the updated schedule
            return new ResponseEntity<>("Schedule updated successfully", HttpStatus.OK); // Return success response
        } else {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND); // Return not found response if the schedule doesn't exist
        }
    }

    @Override
    public ResponseEntity<String> deleteSchedule(String scheduleId) {
        // Check if the schedule exists before attempting to delete it
        Optional<Schedule> existingSchedule = scheduleRepository.findByScheduleId(scheduleId);
        if (existingSchedule.isPresent()) {
            scheduleRepository.delete(existingSchedule.get()); // Delete the found schedule
            return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK); // Return success response
        } else {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND); // Return not found response if the schedule doesn't exist
        }
    }

    @Override
    public ResponseEntity<String> deleteAllSchedules() {
        try {
            scheduleRepository.deleteAll(); // Deletes all entries from the schedule table
            return ResponseEntity.ok("All schedules deleted successfully."); // Return success response
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete schedules."); // Handle exceptions and return error response
        }
    }
}
