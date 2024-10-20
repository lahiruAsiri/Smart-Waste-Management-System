package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Data Transfer Object (DTO) for transferring schedule data
@Data
@AllArgsConstructor // Generates a constructor with all fields
@NoArgsConstructor // Generates a default constructor with no arguments
public class ScheduleDto {
    private String scheduleId; // Identifier for the specific schedule
    private List<String> smartBins; // List of bin IDs associated with this schedule
    private String driverId; // Identifier for the driver assigned to this schedule
    private String time; // Scheduled time for the bins collection or maintenance
    private String route; // Route that the driver will take for this schedule
}
