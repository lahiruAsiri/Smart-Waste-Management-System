package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private String driverId; // Unique driver ID
    private String driverName; // Driver's name
    private boolean available; // Availability status
}
