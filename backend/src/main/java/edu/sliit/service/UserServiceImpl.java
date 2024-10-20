package edu.sliit.service;

import edu.sliit.config.ModelMapperSingleton;
import edu.sliit.document.Collector;
import edu.sliit.repository.CollectorRepository;
import edu.sliit.util.Constants;
import edu.sliit.document.User;
import edu.sliit.dto.UserRequestDto;
import edu.sliit.dto.UserResponseDto;
import edu.sliit.repository.UserRepository;
import edu.sliit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.List;

/**
 * Implementation of the UserService interface.
 * Provides methods to manage user-related operations, such as creating, updating,
 * and retrieving users, while handling various exceptions related to data validation
 * and database interactions.
 *
 * This service includes methods for:
 * - Adding a user
 * - Fetching users by credentials or username
 * - Updating user status and points
 * - Retrieving user status and points
 *
 * @see UserService
 * @see User
 * @see UserRepository
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CollectorRepository collectorRepository;
    private final ModelMapper modelMapper = ModelMapperSingleton.getInstance();
    // Use the Singleton



    /**
     * Creates a new user by generating a unique user ID and saving the user data
     * into the repository. Returns a success message upon successful creation.
     *
     * @param userRequestDto The user details provided by the client.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If invalid data is provided.
     * @throws EntityNotFoundException If an entity is not found during the operation.
     * @throws Exception If an unexpected error occurs.
     */
    @Override
    public ResponseEntity<String> createUser(UserRequestDto userRequestDto) {
        try {
            List<User> existingUser =userRepository.findByUsername(userRequestDto.getUsername());
            List<User> existingEmails =userRepository.findByEmail(userRequestDto.getEmail());
            if (!existingUser.isEmpty() && !existingEmails.isEmpty()) {
                log.error(Constants.USERS_ALREADY + userRequestDto.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Constants.USERS_ALREADY + userRequestDto.getUsername());
            }

            // Generate a unique user ID
            long userCount = userRepository.count();
            String generatedUserId = Constants.USER_ID_PREFIX + (userCount + 1);

            // Map the incoming DTO to the User entity
            User userEntity = modelMapper.map(userRequestDto, User.class);
            userEntity.setUserId(generatedUserId);

            // Save the user entity to the repository
            userRepository.save(userEntity);

            log.info(Constants.USER_ADDED_SUCCESS + generatedUserId);
            return ResponseEntity.ok(Constants.USER_ADDED_SUCCESS + generatedUserId);
        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED, ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.ENTITY_NOT_FOUND, ex);
            return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.ENTITY_NOT_FOUND + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR).body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of users that match the provided username and password.
     * Converts each User entity into a UserResponseDto.
     *
     * @param username The username to match.
     * @param password The password to match.
     * @return List<UserResponseDto> A list of matching users.
     * @throws EntityNotFoundException If no users are found with the provided credentials.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override

    public List<UserResponseDto> getUsersByCredentials(String username, String password) {
        try {
            List<User> userList = userRepository.findByUsernameAndPassword(username, password);
            if (userList.isEmpty()) {
                log.warn(Constants.NO_USERS_FOUND_CREDENTIALS);
                throw new EntityNotFoundException(Constants.NO_USERS_FOUND_CREDENTIALS);
            }

            // Convert the list of users to UserResponseDto using the configured modelMapper
            return userList.stream()
                    .map(user -> modelMapper.map(user, UserResponseDto.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.ENTITY_NOT_FOUND, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }


    /**
     * Retrieves a list of users by matching the username.
     *
     * @param username The username to search for.
     * @return List<UserResponseDto> A list of matching users.
     * @throws EntityNotFoundException If no users are found with the provided username.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public List<UserResponseDto> getUsersByUsername(String username) {
        try {
            // Fetch users by username
            List<User> userList = userRepository.findByUsername(username);

            // If no users were found, log and throw an exception
            if (userList.isEmpty()) {
                log.warn(Constants.NO_USERS_FOUND_USERNAME + username);
                throw new EntityNotFoundException(Constants.NO_USERS_FOUND_USERNAME + username);
            }

            // Convert users to UserResponseDto
            return userList.stream()
                    .map(user -> modelMapper.map(user, UserResponseDto.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.ENTITY_NOT_FOUND, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Updates the status of an existing user by their user ID.
     *
     * @param userId The ID of the user whose status will be updated.
     * @param status The new status to set.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If the provided status is invalid.
     * @throws EntityNotFoundException If the user is not found.
     * @throws Exception If an unexpected error occurs during the update.
     */
    @Override
    public ResponseEntity<String> updateUserStatus(String userId, String status) {
        try {
            // Find the user by their ID
            Optional<User> optionalUser = userRepository.findById(userId);

            // If the user exists, update the status and save
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setStatus(status);
                userRepository.save(user);
                log.info(Constants.STATUS_UPDATED_SUCCESS + userId);
                return ResponseEntity.ok(Constants.STATUS_UPDATED_SUCCESS + userId);
            } else {
                // If the user is not found, return a 404 response
                log.warn(Constants.USER_NOT_FOUND + userId);
                return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.USER_NOT_FOUND + userId);
            }
        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED, ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR).body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }

    /**
     * Updates the points of an existing user by their user ID.
     *
     * @param userId The ID of the user whose points will be updated.
     * @param points The new points value to set.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If the points are invalid.
     * @throws EntityNotFoundException If the user is not found.
     * @throws Exception If an unexpected error occurs during the update.
     */
    @Override
    public ResponseEntity<String> updateUserPoints(String userId, Number points) {
        try {
            // Find the user by their ID
            Optional<User> optionalUser = userRepository.findById(userId);

            // If the user exists, update the points and save
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setPoints(points);
                userRepository.save(user);
                log.info(Constants.POINTS_UPDATED_SUCCESS + userId);
                return ResponseEntity.ok(Constants.POINTS_UPDATED_SUCCESS + userId);
            } else {
                // If the user is not found, return a 404 response
                log.warn(Constants.USER_NOT_FOUND + userId);
                return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.USER_NOT_FOUND + userId);
            }
        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED, ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR).body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }

    /**
     * Retrieves the status of a user by their user ID.
     *
     * @param userId The ID of the user whose status is being retrieved.
     * @return String The status of the user, or an error message if not found.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public String getUserStatus(String userId) {
        try {
            // Fetch the user by their ID
            Optional<User> optionalUser = userRepository.findById(userId);

            // If the user is found, return the status
            if (optionalUser.isPresent()) {
                log.info("Retrieved status for User ID: {}", userId);
                return optionalUser.get().getStatus();
            } else {
                // If the user is not found, return an error message
                log.warn(Constants.USER_NOT_FOUND + userId);
                return Constants.USER_NOT_FOUND + userId;
            }
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            return Constants.INTERNAL_SERVER_ERROR + ex.getMessage();
        }
    }

    /**
     * Retrieves the points of a user by their user ID.
     *
     * @param userId The ID of the user whose points are being retrieved.
     * @return Number The points of the user, or null if not found.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public Number getUserPoints(String userId) {
        try {
            // Fetch the user by their ID
            Optional<User> optionalUser = userRepository.findById(userId);

            // If the user is found, return their points
            if (optionalUser.isPresent()) {
                log.info("Retrieved points for User ID: {}", userId);
                return optionalUser.get().getPoints();
            } else {
                // If the user is not found, return null
                log.warn(Constants.USER_NOT_FOUND + userId);
                return null;
            }
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR, ex);
            return null;
        }
    }




    public Map<Integer, Long> getCollectionCountByYear(String userid) {
        List<Collector> collectorList = collectorRepository.findAllByUserId(userid);

        try {
            // Group by year and count the collections in each year
            Map<Integer, Long> collectionsByYear = collectorList.stream()
                    .collect(Collectors.groupingBy(collector -> {
                        // Convert the collection date to LocalDate
                        LocalDate collectionDate = collector.getCollectionDate().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        // Extract the year from the collection date
                        return collectionDate.getYear();
                    }, Collectors.counting()));

            return collectionsByYear;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
