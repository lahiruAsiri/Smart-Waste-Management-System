package edu.sliit.service;

import edu.sliit.dto.DriverDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {

    /**
     * Adds a new driver to the system.
     *
     * @param dto the driver data transfer object containing driver details
     * @return a ResponseEntity indicating the result of the operation
     */
    ResponseEntity<String> addDriver(DriverDto dto);

    /**
     * Retrieves a driver by their unique ID.
     *
     * @param driverId the ID of the driver to be retrieved
     * @return a DriverDto containing the driver's details, or null if not found
     */
    DriverDto getDriverById(String driverId);

    /**
     * Retrieves a list of all drivers in the system.
     *
     * @return a List of DriverDto objects representing all drivers
     */
    List<DriverDto> getAllDrivers();

    /**
     * Updates the details of an existing driver.
     *
     * @param dto the driver data transfer object containing updated driver details
     * @return a ResponseEntity indicating the result of the update operation
     */
    ResponseEntity<String> updateDriver(DriverDto dto);

    /**
     * Deletes a driver from the system by their unique ID.
     *
     * @param driverId the ID of the driver to be deleted
     * @return a ResponseEntity indicating the result of the deletion operation
     */
    ResponseEntity<String> deleteDriver(String driverId);

    /**
     * Deletes all drivers from the system.
     *
     * @return a ResponseEntity indicating the result of the deletion operation
     */
    ResponseEntity<String> deleteAllDrivers();
}
