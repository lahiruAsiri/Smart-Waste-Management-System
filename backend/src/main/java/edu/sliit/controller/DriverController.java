package edu.sliit.controller;

import edu.sliit.dto.DriverDto;
import edu.sliit.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/add")
    public ResponseEntity<String> addDriver(@RequestBody DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }

    @GetMapping("/{driverId}")
    public DriverDto getDriver(@PathVariable String driverId) {
        return driverService.getDriverById(driverId);
    }

    @GetMapping("/all")
    public List<DriverDto> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<String> deleteDriver(@PathVariable String driverId) {
        return driverService.deleteDriver(driverId);
    }
}
