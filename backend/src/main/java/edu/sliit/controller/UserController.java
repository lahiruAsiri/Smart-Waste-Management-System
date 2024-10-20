package edu.sliit.controller;

import edu.sliit.dto.UserResponseDto;
import edu.sliit.dto.UserRequestDto;
import edu.sliit.service.UserService;
import edu.sliit.util.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle user-related operations such as registration,
 * retrieval of user details, and other user actions.
 */
@RestController
@Slf4j
@RequestMapping(Constants.USERS_BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    /**
     * Registers a new user with the system using the provided UserRequestDto.
     * @param userRequestDto Data transfer object containing user registration details.
     * @return ResponseEntity indicating the result of the registration operation.
     */
    @PostMapping(Constants.USER_REGISTER_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto) {
        log.info(Constants.LOG_REGISTERING_USER, userRequestDto);
        return userService.createUser(userRequestDto);
    }

    /**
     * Retrieves a list of users that match the provided username and password.
     * @param username The username to search for.
     * @param password The password for the user.
     * @return List of UserResponseDto objects representing users that match the credentials.
     */
    @GetMapping(Constants.USER_LIST_URL)
    public List<UserResponseDto> getUsersByCredentials(@NotNull @RequestParam String username, @RequestParam String password) {
        log.info(Constants.LOG_FETCHING_USERS_BY_CREDENTIALS, username);
        return userService.getUsersByCredentials(username, password);
    }

    /**
     * Retrieves a list of users based on the provided user ID.
     * @param userId The ID of the user to search for.
     * @return List of UserResponseDto objects matching the user ID.
     */
    @GetMapping(Constants.USER_FIND_BY_ID_URL)
    public List<UserResponseDto> getUsersById(@NotNull @RequestParam String userId) {
        log.info(Constants.LOG_FETCHING_USER_BY_ID, userId);
        return userService.getUsersByUsername(userId);
    }

    /**
     * Retrieves the status of a user based on the provided user ID.
     * @param userId The ID of the user whose status is to be fetched.
     * @return String representing the status of the user.
     */
    @GetMapping(Constants.USER_STATUS_URL)
    public String getUserStatus(@NotNull @RequestParam String userId) {
        log.info(Constants.LOG_FETCHING_STATUS_FOR_USER, userId);
        return userService.getUserStatus(userId);
    }

    /**
     * Retrieves the point balance of a user based on the provided user ID.
     * @param userId The ID of the user whose points are to be fetched.
     * @return Number representing the point balance of the user.
     */
    @GetMapping(Constants.USER_POINTS_URL)
    public Number getUserPoints(@NotNull @RequestParam String userId) {
        log.info(Constants.LOG_FETCHING_POINTS_FOR_USER, userId);
        return userService.getUserPoints(userId);
    }
}
