package edu.sliit.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// Class representing a schedule for smart bins
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Schedules") // Specifies that this class is a MongoDB document and the collection name is "Schedules"
public class Schedule {
    @Id
    private String id; // Unique identifier for the schedule document
    private String scheduleId; // Identifier for the specific schedule
    private List<String> smartBins; // List of bin IDs associated with this schedule
    private String driverId; // Identifier for the driver assigned to this schedule
    private String time; // Scheduled time for the bins collection or maintenance
    private String route; // Route that the driver will take for this schedule
}
