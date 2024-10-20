package edu.sliit.service;

import edu.sliit.dto.ScheduleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

// Service interface for managing schedules
public interface ScheduleService {
    // Method to add a new schedule
    ResponseEntity<String> addSchedule(ScheduleDto dto);

    // Method to retrieve all schedules
    List<ScheduleDto> getAllSchedules();

    // Method to retrieve a schedule by its ID
    ScheduleDto getScheduleById(String scheduleId);

    // Method to update an existing schedule
    ResponseEntity<String> updateSchedule(ScheduleDto dto);

    // Method to delete a schedule by its ID
    ResponseEntity<String> deleteSchedule(String scheduleId);

    // Method to delete all schedules
    ResponseEntity<String> deleteAllSchedules();
}
