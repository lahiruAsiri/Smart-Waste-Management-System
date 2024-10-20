package edu.sliit.service;

import edu.sliit.dto.UserResponseDto;
import edu.sliit.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
     ResponseEntity<String> createUser(UserRequestDto dto);
     List<UserResponseDto> getUsersByCredentials(String username, String password);
     List<UserResponseDto> getUsersByUsername(String username);
     ResponseEntity<String> updateUserStatus (String userid,String status);
     ResponseEntity<String> updateUserPoints (String userid,Number points);
     String getUserStatus(String userid);
     Number getUserPoints(String userid);

}
