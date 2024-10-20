package edu.sliit.service;

import edu.sliit.dto.CollectorDto;
import edu.sliit.dto.GetPaymentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service interface for managing collector-related operations.
 * It defines methods for creating collectors and retrieving collection details.
 */
public interface CollectorService {

    /**
     * Creates a new collector based on the provided CollectorDto.
     *
     * @param collectorDto Data Transfer Object containing details for the collector to be created.
     * @return ResponseEntity with a success message or error details.
     */
    ResponseEntity<String> createCollector(CollectorDto collectorDto);

    /**
     * Retrieves all collection details for a specific user ID.
     *
     * @param userId The ID of the user whose collection details are being retrieved.
     * @return List of GetPaymentDto objects containing collection details for the specified user.
     */
    List<GetPaymentDto> getAllCollectionByUserId(String userId);
}
