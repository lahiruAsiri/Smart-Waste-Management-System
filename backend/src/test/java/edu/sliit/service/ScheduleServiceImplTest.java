package edu.sliit.service;

import edu.sliit.document.Schedule;
import edu.sliit.dto.ScheduleDto;
import edu.sliit.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private ScheduleDto scheduleDto;
    private Schedule schedule;

    @BeforeEach
    public void setUp() {
        scheduleDto = new ScheduleDto("123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");
        schedule = new Schedule(null, "123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");
    }

    // Positive tests
    @Test
    public void testAddSchedule() {
        // Arrange
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Act
        ResponseEntity<String> response = scheduleService.addSchedule(scheduleDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Schedule added successfully", response.getBody());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    public void testGetAllSchedules() {
        // Arrange
        when(scheduleRepository.findAll()).thenReturn(Collections.singletonList(schedule));

        // Act
        var result = scheduleService.getAllSchedules();

        // Assert
        assertEquals(1, result.size());
        assertEquals(scheduleDto.getScheduleId(), result.get(0).getScheduleId());
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    public void testGetScheduleById() {
        // Arrange
        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.of(schedule));

        // Act
        var result = scheduleService.getScheduleById("123");

        // Assert
        assertEquals(scheduleDto.getScheduleId(), result.getScheduleId());
        verify(scheduleRepository, times(1)).findByScheduleId("123");
    }

    @Test
    public void testUpdateSchedule() {
        // Arrange
        when(scheduleRepository.findByScheduleId("Naj")).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // Act
        ResponseEntity<String> response = scheduleService.updateSchedule(scheduleDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Schedule updated successfully", response.getBody());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    public void testDeleteSchedule() {
        // Arrange
        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.of(schedule));

        // Act
        ResponseEntity<String> response = scheduleService.deleteSchedule("123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(scheduleRepository, times(1)).delete(any(Schedule.class));
    }

    // Negative tests
    @Test
    public void testAddSchedule_Failure() {
        // Arrange
        when(scheduleRepository.save(any(Schedule.class))).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<String> response = scheduleService.addSchedule(scheduleDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to add schedule", response.getBody());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    public void testGetScheduleById_NotFound() {
        // Arrange
        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.empty());

        // Act
        var result = scheduleService.getScheduleById("123");

        // Assert
        assertEquals(null, result); // Assuming the service returns null if not found
        verify(scheduleRepository, times(1)).findByScheduleId("123");
    }

    @Test
    public void testUpdateSchedule_NotFound() {
        // Arrange
        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = scheduleService.updateSchedule(scheduleDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Schedule not found", response.getBody());
        verify(scheduleRepository, times(0)).save(any(Schedule.class)); // No save should occur
    }

    @Test
    public void testDeleteSchedule_NotFound() {
        // Arrange
        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = scheduleService.deleteSchedule("123");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Schedule not found", response.getBody());
        verify(scheduleRepository, times(0)).delete(any(Schedule.class)); // No delete should occur
    }
}









//package edu.sliit.service;
//
//
//import edu.sliit.document.Schedule;
//import edu.sliit.dto.ScheduleDto;
//import edu.sliit.repository.ScheduleRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ScheduleServiceImplTest {
//
//    @Mock
//    private ScheduleRepository scheduleRepository;
//
//    @InjectMocks
//    private ScheduleServiceImpl scheduleService;
//
//    private ScheduleDto scheduleDto;
//    private Schedule schedule;
//
//    @BeforeEach
//    public void setUp() {
//        scheduleDto = new ScheduleDto("123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");
//        schedule = new Schedule(null, "123", Collections.singletonList("bin1"), "driver1", "10:00", "Route A");
//    }
//
//    @Test
//    public void testAddSchedule() {
//        // Arrange
//        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
//
//        // Act
//        ResponseEntity<String> response = scheduleService.addSchedule(scheduleDto);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("Schedule added successfully", response.getBody());
//        verify(scheduleRepository, times(1)).save(any(Schedule.class));
//    }
//
//    @Test
//    public void testGetAllSchedules() {
//        // Arrange
//        when(scheduleRepository.findAll()).thenReturn(Collections.singletonList(schedule));
//
//        // Act
//        var result = scheduleService.getAllSchedules();
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals(scheduleDto.getScheduleId(), result.get(0).getScheduleId());
//        verify(scheduleRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetScheduleById() {
//        // Arrange
//        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.of(schedule));
//
//        // Act
//        var result = scheduleService.getScheduleById("123");
//
//        // Assert
//        assertEquals(scheduleDto.getScheduleId(), result.getScheduleId());
//        verify(scheduleRepository, times(1)).findByScheduleId("123");
//    }
//
//    @Test
//    public void testUpdateSchedule() {
//        // Arrange
//        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.of(schedule));
//        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
//
//        // Act
//        ResponseEntity<String> response = scheduleService.updateSchedule(scheduleDto);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Schedule updated successfully", response.getBody());
//        verify(scheduleRepository, times(1)).save(any(Schedule.class));
//    }
//
//    @Test
//    public void testDeleteSchedule() {
//        // Arrange
//        when(scheduleRepository.findByScheduleId("123")).thenReturn(Optional.of(schedule));
//
//        // Act
//        ResponseEntity<String> response = scheduleService.deleteSchedule("123");
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(scheduleRepository, times(1)).delete(any(Schedule.class));
//    }
//}
