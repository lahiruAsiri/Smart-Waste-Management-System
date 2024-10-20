package edu.sliit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBinDto {
    private String binid;
    private String UserId ;
    private String BinType;
    private String Capacity;
    private String Location ;
}
