package edu.sliit.service;

import edu.sliit.document.User;
import edu.sliit.dto.UserRequestDto;
import edu.sliit.dto.UserResponseDto;
import edu.sliit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // createUser tests
    @Test
    void testCreateUser_Success() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("newuser");
        userRequestDto.setEmail("newuser@example.com");

        when(userRepository.findByUsername(anyString())).thenReturn(new ArrayList<>());
        when(userRepository.findByEmail(anyString())).thenReturn(new ArrayList<>());
        when(userRepository.count()).thenReturn(0L);

        ResponseEntity<String> response = userService.createUser(userRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("User added successfully"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("existinguser");
        userRequestDto.setEmail("existing@example.com");

        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(new User());

        when(userRepository.findByUsername(anyString())).thenReturn(existingUsers);
        when(userRepository.findByEmail(anyString())).thenReturn(existingUsers);

        ResponseEntity<String> response = userService.createUser(userRequestDto);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().contains("User already exists"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_EmptyUsername() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("");
        userRequestDto.setEmail("user@example.com");

        ResponseEntity<String> response = userService.createUser(userRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid data provided"));
        verify(userRepository, never()).save(any(User.class));
    }

    // getUsersByCredentials tests
    @Test
    void testGetUsersByCredentials_Success() {
        String username = "testuser";
        String password = "password";
        List<User> userList = new ArrayList<>();
        userList.add(new User());

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(userList);

        List<UserResponseDto> result = userService.getUsersByCredentials(username, password);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByUsernameAndPassword(username, password);
    }

    @Test
    void testGetUsersByCredentials_UserNotFound() {
        String username = "nonexistent";
        String password = "wrongpassword";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> userService.getUsersByCredentials(username, password));
        verify(userRepository, times(1)).findByUsernameAndPassword(username, password);
    }

    @Test
    void testGetUsersByCredentials_EmptyCredentials() {
        String username = "";
        String password = "";

        assertThrows(IllegalArgumentException.class, () -> userService.getUsersByCredentials(username, password));
        verify(userRepository, never()).findByUsernameAndPassword(anyString(), anyString());
    }

    // getUsersByUsername tests
    @Test
    void testGetUsersByUsername_Success() {
        String username = "testuser";
        List<User> userList = new ArrayList<>();
        userList.add(new User());

        when(userRepository.findByUsername(username)).thenReturn(userList);

        List<UserResponseDto> result = userService.getUsersByUsername(username);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetUsersByUsername_UserNotFound() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, () -> userService.getUsersByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testGetUsersByUsername_EmptyUsername() {
        String username = "";

        assertThrows(IllegalArgumentException.class, () -> userService.getUsersByUsername(username));
        verify(userRepository, never()).findByUsername(anyString());
    }

    // updateUserStatus tests
    @Test
    void testUpdateUserStatus_Success() {
        String userId = "USER001";
        String newStatus = "ACTIVE";
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userService.updateUserStatus(userId, newStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Status updated successfully"));
        assertEquals(newStatus, user.getStatus());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserStatus_UserNotFound() {
        String userId = "NONEXISTENT";
        String newStatus = "INACTIVE";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userService.updateUserStatus(userId, newStatus);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("User not found"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserStatus_EmptyStatus() {
        String userId = "USER001";
        String newStatus = "";

        ResponseEntity<String> response = userService.updateUserStatus(userId, newStatus);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid data provided"));
        verify(userRepository, never()).save(any(User.class));
    }

    // updateUserPoints tests
    @Test
    void testUpdateUserPoints_Success() {
        String userId = "USER001";
        Number newPoints = 100;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userService.updateUserPoints(userId, newPoints);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Points updated successfully"));
        assertEquals(newPoints, user.getPoints());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserPoints_UserNotFound() {
        String userId = "NONEXISTENT";
        Number newPoints = 50;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userService.updateUserPoints(userId, newPoints);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("User not found"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserPoints_NegativePoints() {
        String userId = "USER001";
        Number newPoints = -10;

        ResponseEntity<String> response = userService.updateUserPoints(userId, newPoints);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid data provided"));
        verify(userRepository, never()).save(any(User.class));
    }

    // getUserStatus tests
    @Test
    void testGetUserStatus_Success() {
        String userId = "USER001";
        String expectedStatus = "ACTIVE";
        User user = new User();
        user.setUserId(userId);
        user.setStatus(expectedStatus);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.getUserStatus(userId);

        assertEquals(expectedStatus, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserStatus_UserNotFound() {
        String userId = "NONEXISTENT";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        String result = userService.getUserStatus(userId);

        assertTrue(result.contains("User not found"));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserStatus_EmptyUserId() {
        String userId = "";

        String result = userService.getUserStatus(userId);

        assertTrue(result.contains("Invalid user ID"));
        verify(userRepository, never()).findById(anyString());
    }

    // getUserPoints tests
    @Test
    void testGetUserPoints_Success() {
        String userId = "USER001";
        Number expectedPoints = 150;
        User user = new User();
        user.setUserId(userId);
        user.setPoints(expectedPoints);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Number result = userService.getUserPoints(userId);

        assertEquals(expectedPoints, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserPoints_UserNotFound() {
        String userId = "NONEXISTENT";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Number result = userService.getUserPoints(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserPoints_EmptyUserId() {
        String userId = "";

        Number result = userService.getUserPoints(userId);

        assertNull(result);
        verify(userRepository, never()).findById(anyString());
    }
}