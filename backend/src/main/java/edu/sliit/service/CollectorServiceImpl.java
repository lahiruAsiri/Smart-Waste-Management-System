package edu.sliit.service;

import edu.sliit.config.ModelMapperSingleton;
import edu.sliit.document.Collector;
import edu.sliit.dto.CollectorDto;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.repository.CollectorRepository;
import edu.sliit.service.CollectorService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CollectorService interface.
 * Provides methods to manage Collector-related operations, such as creating,
 * and retrieving Collector, while handling various exceptions related to data validation
 * and database interactions.
 *
 * This service includes methods for:
 * - Adding a Collector
 * - Fetching Collector by credentials or userid
 *
 *
 * @see CollectorService
 * @see Collector
 * @see CollectorRepository
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CollectorServiceImpl implements CollectorService {

    private final CollectorRepository collectorRepository;
//    private final ModelMapper modelMapper;
    private final ModelMapper modelMapper = ModelMapperSingleton.getInstance();  // Use the Singleton

    /**
     * Creates a new Collector by generating a unique user ID and saving the Collector data
     * into the repository. Returns a success message upon successful creation.
     *
     * @param collectorDto The Collector details provided by the client.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If invalid data is provided.
     * @throws EntityNotFoundException If an entity is not found during the operation.
     * @throws Exception If an unexpected error occurs.
     */
    @Override
    public ResponseEntity<String> createCollector(CollectorDto collectorDto) {
        try {
            long collectorCount = collectorRepository.count();
            String generatedCollectorId = "COLL" + (collectorCount + 1);
            Collector collectedEntity = modelMapper.map(collectorDto, Collector.class);
            collectedEntity.setCollectorId(generatedCollectorId);
            collectedEntity.setId(new ObjectId().toString());
            collectorRepository.save(collectedEntity);
            return ResponseEntity.ok(Constants.COLLECTION_ADDED_SUCCESS + collectedEntity.getCollectorId());

        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED + ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.USER_NOT_FOUND + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.USER_NOT_FOUND + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR)
                    .body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of Collector that match the provided userid.
     * Converts each User entity into a GetPaymentDto.
     *
     * @param userid The userid to match.
     * @return List<GetPaymentDto> A list of matching Collector.
     * @throws EntityNotFoundException If no Collector are found with the provided credentials.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public List<GetPaymentDto> getAllCollectionByUserId(String userid) {
        List<Collector> collectorList = collectorRepository.findAllByUserId(userid);
        try {
            return collectorList.stream()
                    .map(collector -> modelMapper.map(collector, GetPaymentDto.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.PAYMENT_NOT_FOUND + userid, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }





}
