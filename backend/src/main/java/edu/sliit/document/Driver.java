package edu.sliit.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Drivers")
public class Driver {
    @Id
    private String id; // Auto-generated ID by MongoDB
    private String driverId; // Unique driver ID
    private String driverName; // Driver's name
    private boolean available; // Availability status
}
