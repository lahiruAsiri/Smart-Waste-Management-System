package edu.sliit.controller;


import edu.sliit.dto.CollectorDto;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.service.CollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * This is the REST controller for handling Collector-related requests.
 * It provides endpoints to add a collector and retrieve collector details.
 */
@RestController
@Slf4j
@RequestMapping("collector")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CollectorController {

    private final CollectorService collectorService;

    /**
     * Endpoint to add a new collector.
     *
     * @param collectorDto Contains the details of the collector to be added.
     * @return ResponseEntity with a success message or error details.
     */
    @PostMapping("addcollecter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> CreateCollector (@RequestBody CollectorDto collectorDto) {
        log.info("User Registration for [{}]", collectorDto);
        return collectorService.createCollector(collectorDto);
    }
    /**
     * Endpoint to get payment details for a specific collector.
     *
     * @param userid The ID of the collector whose payment details are requested.
     * @return List of GetPaymentDto containing payment details for the specified collector.
     */
    @GetMapping("getcollecterdetails")
    public List<GetPaymentDto> getAllCollectionByUserId (String userid){
        return collectorService.getAllCollectionByUserId(userid);
    }

}
