package edu.sliit.controller;

import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.service.BinService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller to handle Bin-related operations.
 * It provides endpoints to add new bins and retrieve bin details for a user.
 */

@RestController
@Slf4j
@RequestMapping(Constants.BIN_BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BinController {

    private final BinService binService;

    /**
     * Endpoint to add a new bin.
     *
     * @param binDto Data Transfer Object containing the bin details to be added.
     * @return ResponseEntity with a success message or error details.
     */
    @PostMapping(Constants.BIN_ADD_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addBin(@RequestBody BinDto binDto) {
        log.info(Constants.LOG_CREATING_BIN, binDto);
        return binService.createBin(binDto);
    }

    /**
     * Endpoint to get all bin details for a specific user.
     *
     * @param userid The ID of the user whose bin details are requested.
     * @return List of GetBinDto objects containing bin details for the specified user.
     */
    @GetMapping(Constants.BIN_GET_DETAILS_URL)
    public List<GetBinDto> getAllDetails(@RequestParam String userid) {
        log.info(Constants.LOG_FETCHING_BIN_DETAILS, userid);
        return binService.getAllBinByUserId(userid);
    }


    /**
     * Endpoint to update the status of a bin collection based on the provided binId and new status.
     *
     * @param binId     The ID of the bin collection.
     * @param newStatus The new status to be set for the bin collection.
     * @return ResponseEntity<String> indicating success or failure of the update.
     */
    @PutMapping("/{binId}/status")
    public ResponseEntity<String> updateBinCollectionStatus(@PathVariable String binId, @RequestParam String newStatus) {
        log.info("Updating bin status for binId: {}, newStatus: {}", binId, newStatus);
        return binService.updateBinCollectionStatus(binId, newStatus);
    }

    /**
     * Endpoint to get the collection count grouped by month for a specific bin.
     *
     * @param binId The ID of the bin.
     * @return Map<String, Long> with the month as the key and the collection count as the value.
     */
    @GetMapping("/{binId}/collections/monthly")
    public ResponseEntity<Map<String, Long>> getCollectionCountByMonth(@PathVariable String binId) {
        log.info("Fetching collection count by month for binId: {}", binId);
        Map<String, Long> collectionsByMonth = binService.getCollectionCountByMonth(binId);
        return ResponseEntity.ok(collectionsByMonth);
    }

    /**
     * Endpoint to get the collection count grouped by month and the total count for a specific bin.
     *
     * @param binId The ID of the bin.
     * @return Map<String, Object> with the month as the key and the collection count as the value, along with the total count.
     */
    @GetMapping("/{binId}/collections/monthly-total")
    public ResponseEntity<Object> getCollectionCountByMonthAndTotal(@PathVariable String binId) {
        log.info("Fetching collection count by month and total for binId: {}", binId);
        Map<String, Object> collectionsByMonthAndTotal = binService.getCollectionCountByMonthAndTotal(binId);
        return ResponseEntity.ok(collectionsByMonthAndTotal);
    }
}
