package edu.sliit.service;

import edu.sliit.document.Driver;
import edu.sliit.dto.DriverDto;
import edu.sliit.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceImpl driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for Null DTO in addDriver method
    @Test
    void testAddDriver_NullDriverDto_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> driverService.addDriver(null));
    }

    // Test for Null DriverId in getDriverById method
    @Test
    void testGetDriverById_NullDriverId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> driverService.getDriverById(null));
    }

    // Test for Empty DriverId in getDriverById method
    @Test
    void testGetDriverById_EmptyDriverId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> driverService.getDriverById(""));
    }

    // Test for Null DTO in updateDriver method
    @Test
    void testUpdateDriver_NullDriverDto_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> driverService.updateDriver(null));
    }

    // Test for Null DriverId in deleteDriver method
    @Test
    void testDeleteDriver_NullDriverId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> driverService.deleteDriver(null));
    }

    // Test for Empty DriverId in deleteDriver method
    @Test
    void testDeleteDriver_EmptyDriverId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> driverService.deleteDriver(""));
    }

    // Positive case for addDriver
    @Test
    void testAddDriver_Positive() {
        DriverDto driverDto = new DriverDto("D001", "John Doe", true);
        Driver driver = new Driver(null, "D001", "John Doe", true);

        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        ResponseEntity<String> response = driverService.addDriver(driverDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Driver added successfully", response.getBody());

        verify(driverRepository, times(1)).save(any(Driver.class));
    }

    // Example of handling invalid driverId in getDriverById
    @Test
    void testGetDriverById_InvalidDriverId_ThrowsException() {
        String invalidDriverId = "INVALID_ID";
        when(driverRepository.findByDriverId(invalidDriverId)).thenReturn(Optional.empty());

        DriverDto result = driverService.getDriverById(invalidDriverId);
        assertNull(result);  // Ensure the result is null for invalid id

        verify(driverRepository, times(1)).findByDriverId(invalidDriverId);
    }

    // Test for updating existing driver (Positive)
    @Test
    void testUpdateDriver_Positive() {
        Driver existingDriver = new Driver("1", "D001", "John Doe", true);
        DriverDto updatedDriverDto = new DriverDto("D001", "Johnny", false);

        when(driverRepository.findByDriverId("D001")).thenReturn(Optional.of(existingDriver));

        ResponseEntity<String> response = driverService.updateDriver(updatedDriverDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver updated successfully", response.getBody());

        verify(driverRepository, times(1)).save(existingDriver);
    }

    // Test for updating a non-existing driver (Negative)
    @Test
    void testUpdateDriver_Negative() {
        DriverDto driverDto = new DriverDto("D002", "John Doe", true);

        when(driverRepository.findByDriverId("D002")).thenReturn(Optional.empty());

        ResponseEntity<String> response = driverService.updateDriver(driverDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Driver not found", response.getBody());

        verify(driverRepository, times(1)).findByDriverId("D002");
    }

    // Test for deleting an existing driver (Positive)
    @Test
    void testDeleteDriver_Positive() {
        Driver existingDriver = new Driver("1", "D001", "John Doe", true);

        when(driverRepository.findByDriverId("D001")).thenReturn(Optional.of(existingDriver));

        ResponseEntity<String> response = driverService.deleteDriver("D001");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver deleted successfully", response.getBody());

        verify(driverRepository, times(1)).delete(existingDriver);
    }

    // Test for deleting a non-existing driver (Negative)
    @Test
    void testDeleteDriver_Negative() {
        when(driverRepository.findByDriverId("D002")).thenReturn(Optional.empty());

        ResponseEntity<String> response = driverService.deleteDriver("D002");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Driver not found", response.getBody());

        verify(driverRepository, times(1)).findByDriverId("D002");
    }

    // Test for deleting all drivers (Positive)
    @Test
    void testDeleteAllDrivers_Positive() {
        ResponseEntity<String> response = driverService.deleteAllDrivers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("All drivers deleted successfully.", response.getBody());

        verify(driverRepository, times(1)).deleteAll();
    }
}