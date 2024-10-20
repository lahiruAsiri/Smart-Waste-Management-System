package edu.sliit.controller;

import edu.sliit.dto.ScheduleDto; // Importing the Schedule Data Transfer Object
import edu.sliit.exception.ScheduleException; // Importing custom exception for handling schedule-related errors
import edu.sliit.service.ScheduleService; // Importing the service for schedule-related operations
import lombok.RequiredArgsConstructor; // Lombok annotation to automatically generate constructor
import lombok.extern.slf4j.Slf4j; // Lombok annotation for logging
import org.springframework.http.HttpStatus; // Importing HTTP status codes
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for HTTP responses
import org.springframework.web.bind.annotation.*; // Importing Spring annotations for REST controllers

import java.util.List; // Importing List for returning collections

@RestController // Indicates that this class is a REST controller
@Slf4j // Enables logging for this class
@RequestMapping("schedule") // Base URL for this controller
@RequiredArgsConstructor // Automatically generates a constructor with required arguments
@CrossOrigin(origins = "http://localhost:5173") // Allows cross-origin requests from the specified origin
public class ScheduleController {

    private final ScheduleService scheduleService; // Injecting the ScheduleService

    // Endpoint to add a new schedule
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) // Sets the response status to 201 Created
    public ResponseEntity<String> addSchedule(@RequestBody ScheduleDto dto) {
        log.info("Adding new Schedule for [{}]", dto); // Log the action
        try {
            return scheduleService.addSchedule(dto); // Call service method to add the schedule
        } catch (Exception e) {
            log.error("Error adding schedule: {}", e.getMessage()); // Log any errors
            throw new ScheduleException("Error adding schedule: " + e.getMessage()); // Throw a custom exception
        }
    }

    // Endpoint to retrieve all schedules
    @GetMapping("/getSchedules")
    public ResponseEntity<List<ScheduleDto>> getSchedules() {
        try {
            List<ScheduleDto> schedules = scheduleService.getAllSchedules(); // Get all schedules from the service
            return ResponseEntity.ok(schedules); // Return schedules with 200 OK status
        } catch (Exception e) {
            log.error("Error retrieving schedules: {}", e.getMessage()); // Log any errors
            throw new ScheduleException("Error retrieving schedules: " + e.getMessage()); // Throw a custom exception
        }
    }

    // Endpoint to retrieve a schedule by ID
    @GetMapping("/getSchedule")
    public ResponseEntity<ScheduleDto> getSchedule(@RequestParam String scheduleId) {
        try {
            ScheduleDto schedule = scheduleService.getScheduleById(scheduleId); // Retrieve the schedule by ID
            if (schedule == null) {
                throw new ScheduleException("Schedule not found for ID: " + scheduleId); // Throw exception if not found
            }
            return ResponseEntity.ok(schedule); // Return schedule with 200 OK status
        } catch (ScheduleException e) {
            log.error("Error retrieving schedule with ID [{}]: {}", scheduleId, e.getMessage()); // Log specific exception
            throw e; // Rethrow the custom exception
        } catch (Exception e) {
            log.error("Error retrieving schedule with ID [{}]: {}", scheduleId, e.getMessage()); // Log any errors
            throw new ScheduleException("Error retrieving schedule with ID " + scheduleId + ": " + e.getMessage()); // Throw a custom exception
        }
    }

    // Endpoint to update an existing schedule
    @PutMapping("/update/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable String scheduleId, @RequestBody ScheduleDto dto) {
        dto.setScheduleId(scheduleId); // Set the schedule ID in the DTO
        try {
            return scheduleService.updateSchedule(dto); // Call service method to update the schedule
        } catch (Exception e) {
            log.error("Error updating schedule with ID [{}]: {}", scheduleId, e.getMessage()); // Log any errors
            throw new ScheduleException("Error updating schedule with ID " + scheduleId + ": " + e.getMessage()); // Throw a custom exception
        }
    }

    // Endpoint to delete a specific schedule by ID
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable String scheduleId) {
        log.info("Deleting Schedule with ID [{}]", scheduleId); // Log the action
        try {
            return scheduleService.deleteSchedule(scheduleId); // Call service method to delete the schedule
        } catch (Exception e) {
            log.error("Error deleting schedule with ID [{}]: {}", scheduleId, e.getMessage()); // Log any errors
            throw new ScheduleException("Error deleting schedule with ID " + scheduleId + ": " + e.getMessage()); // Throw a custom exception
        }
    }

    // Endpoint to delete all schedules
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllSchedules() {
        log.info("Deleting all schedules"); // Log the action
        try {
            return scheduleService.deleteAllSchedules(); // Call service method to delete all schedules
        } catch (Exception e) {
            log.error("Error deleting all schedules: {}", e.getMessage()); // Log any errors
            throw new ScheduleException("Error deleting all schedules: " + e.getMessage()); // Throw a custom exception
        }
    }
}
