package edu.sliit.service;

import edu.sliit.document.Driver;
import edu.sliit.dto.DriverDto;
import edu.sliit.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public ResponseEntity<String> addDriver(DriverDto dto) {
        // Create a new Driver object from the provided DTO and save it to the repository
        Driver newDriver = new Driver(null, dto.getDriverId(), dto.getDriverName(), dto.isAvailable());
        driverRepository.save(newDriver);
        return new ResponseEntity<>("Driver added successfully", HttpStatus.CREATED);
    }

    @Override
    public DriverDto getDriverById(String driverId) {
        // Find a driver by their ID and return a DTO representation, or null if not found
        Optional<Driver> driver = driverRepository.findByDriverId(driverId);
        return driver.map(d -> new DriverDto(d.getDriverId(), d.getDriverName(), d.isAvailable()))
                .orElse(null);
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        // Retrieve all drivers from the repository and convert them to DTOs
        return driverRepository.findAll().stream()
                .map(driver -> new DriverDto(driver.getDriverId(), driver.getDriverName(), driver.isAvailable()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> updateDriver(DriverDto dto) {
        // Check if the driver exists, update their details if they do, or return an error if not
        Optional<Driver> existingDriver = driverRepository.findByDriverId(dto.getDriverId());
        if (existingDriver.isPresent()) {
            Driver updatedDriver = existingDriver.get();
            updatedDriver.setDriverName(dto.getDriverName());
            updatedDriver.setAvailable(dto.isAvailable());
            driverRepository.save(updatedDriver);
            return new ResponseEntity<>("Driver updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> deleteDriver(String driverId) {
        // Attempt to find and delete the driver by ID, returning the appropriate response
        Optional<Driver> existingDriver = driverRepository.findByDriverId(driverId);
        if (existingDriver.isPresent()) {
            driverRepository.delete(existingDriver.get());
            return new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> deleteAllDrivers() {
        // Delete all drivers from the repository and return a success message
        driverRepository.deleteAll();
        return ResponseEntity.ok("All drivers deleted successfully.");
    }
}
