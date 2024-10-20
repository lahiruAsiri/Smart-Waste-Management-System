package edu.sliit.service;

import edu.sliit.config.ModelMapperSingleton;
import edu.sliit.document.Bin;
import edu.sliit.document.Collector;
import edu.sliit.document.User;
import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.repository.BinRepository;
import edu.sliit.repository.CollectorRepository;
import edu.sliit.repository.UserRepository;
import edu.sliit.service.BinService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;



/**
 * Implementation of the BinService interface.
 * Provides methods to manage Bin-related operations, such as creating,
 * and retrieving Bin, while handling various exceptions related to data validation
 * and database interactions.
 *
 * This service includes methods for:
 * - Adding a Bin
 * - Fetching Bin by credentials or userid
 *
 * @see BinService
 * @see Bin
 * @see BinRepository
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BinServiceImpl implements BinService {

    private final BinRepository binRepository;
    private final UserRepository userRepository;
    private final CollectorRepository collectorRepository;
    private final ModelMapper modelMapper = ModelMapperSingleton.getInstance();  // Use the Singleton


    /**
     * Creates a new Bin by generating a unique Bin ID and saving the Bin data
     * into the repository. Returns a success message upon successful creation.
     *
     * @param binDto The Bin details provided by the client.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If invalid data is provided.
     * @throws EntityNotFoundException If an entity is not found during the operation.
     * @throws Exception If an unexpected error occurs.
     */
    public ResponseEntity<String> createBin(BinDto binDto) {
        try {
            // Check if a bin already exists for this user

            long binCount = binRepository.count();
            String generatedBinId = Constants.BIN_ID_PREFIX + (binCount + 1);

            // Map BinDto to Bin entity
            Bin binEntity = modelMapper.map(binDto, Bin.class);

            // Ensure user is fetched and location is set properly
            User user = userRepository.findByUserId(binDto.getUserId());
            if (user == null) {
                throw new EntityNotFoundException("User not found with id: " + binDto.getUserId());
            }
            // Let MongoDB generate the ObjectId for _id
            binEntity.setId(new ObjectId().toString());
            binEntity.setLocation(user.getLocation());
            binEntity.setStatus(Constants.STATUS_BIN);
            binEntity.setBinId(generatedBinId);

            // Save bin entity to the repository
            binRepository.save(binEntity);

            return ResponseEntity.ok(Constants.BIN_ADDED_SUCCESS + binEntity.getBinId());
        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED + ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.ENTITY_NOT_FOUND + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.ENTITY_NOT_FOUND + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR)
                    .body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }




    /**
     * Retrieves a list of Bin that match the provided userid.
     * Converts each Bin entity into a GetBinDto.
     *
     * @param userid The userid.
     * @return List<GetBinDto> A list of matching Bin.
     * @throws EntityNotFoundException If no Bin are found with the provided credentials.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public List<GetBinDto> getAllBinByUserId (String userid) {
        try {
            List<Bin> binList = binRepository.findAllByUserId(userid);
            return binList.stream()
                    .map(bin -> modelMapper.map(bin, GetBinDto.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.BIN_NOT_FOUND + userid, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }


    @Override
    public Map<String, Long> getCollectionCountByMonth(String binid) {
        List<Collector> collectorList = collectorRepository.findAllByBinId(binid);
        log.info("collectorList  for [{}]", collectorList);

        try {
            // Group by month and count the collections in each month
            Map<String, Long> collectionsByMonth = collectorList.stream()
                    .collect(Collectors.groupingBy(collector -> {
                        // Convert the collection date to LocalDate
                        LocalDate collectionDate = collector.getCollectionDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        // Get the month name from the collection date
                        return collectionDate.getMonth()
                                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    }, Collectors.counting()));

            return collectionsByMonth;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }
    @Override
    public Map<String, Object> getCollectionCountByMonthAndTotal(String binid) {
        List<Collector> collectorList = collectorRepository.findAllByBinId(binid);
        try {
            // Group by month and count the collections in each month
            Map<String, Long> collectionsByMonth = collectorList.stream()
                    .collect(Collectors.groupingBy(collector -> {
                        // Convert the collection date to LocalDate
                        LocalDate collectionDate = collector.getCollectionDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        // Get the month name from the collection date
                        return collectionDate.getMonth()
                                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    }, Collectors.counting()));

            // Calculate the total number of collections (bin count)
            long totalCollections = collectorList.size();

            // Create a result map with both monthly counts and total count
            Map<String, Object> result = collectionsByMonth.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));

            // Add total count to the result
            result.put("Total", totalCollections);

            return result;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Updates the status of a bin collection based on the provided binId and new status.
     *
     * @param binId The ID of the bin collection.
     * @param newStatus The new status to be set for the bin collection.
     * @return ResponseEntity<String> indicating success or failure of the update.
     */
    @Override
    public ResponseEntity<String> updateBinCollectionStatus(String binId, String newStatus) {
        try {
            // Find the bin collection by binId
            Bin bin = binRepository.findById(binId).orElseThrow(() -> new EntityNotFoundException(Constants.BIN_NOT_FOUND + binId));

            // Update the status
            bin.setStatus(newStatus);

            // Save the updated bin entity
            binRepository.save(bin);

            return ResponseEntity.ok(Constants.BIN_STATUS_UPDATED_SUCCESS + binId);
        } catch (EntityNotFoundException ex) {
            log.error(Constants.BIN_NOT_FOUND + binId, ex);
            return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.BIN_NOT_FOUND + binId);
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR).body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }


}

