package edu.sliit.service;

import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Service interface for managing bin-related operations.
 * It defines methods for creating bins and retrieving bin details.
 */
public interface BinService {

    /**
     * Creates a new bin based on the provided BinDto.
     *
     * @param binDto Data Transfer Object containing details for the bin to be created.
     * @return ResponseEntity with a success message or error details.
     */
    ResponseEntity<String> createBin(BinDto binDto);

    /**
     * Retrieves all bin details for a specific user ID.
     *
     * @param userId The ID of the user whose bin details are being retrieved.
     * @return List of GetBinDto objects containing bin details for the specified user.
     */
    List<GetBinDto> getAllBinByUserId(String userId);

    public Map<String, Long> getCollectionCountByMonth(String binid);
    public Map<String, Object> getCollectionCountByMonthAndTotal(String binid);
    public ResponseEntity<String> updateBinCollectionStatus(String binId, String newStatus);
}
