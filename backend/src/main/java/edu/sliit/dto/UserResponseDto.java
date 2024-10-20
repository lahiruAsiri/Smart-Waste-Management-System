package edu.sliit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponseDto {

    private String UserId;
    private String username;
    private String location;
    private String Status;
    private Number points ;

}
