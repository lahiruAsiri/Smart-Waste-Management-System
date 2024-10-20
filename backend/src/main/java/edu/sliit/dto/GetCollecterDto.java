package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCollecterDto {

    private Number id;
    private String userid ;
    private String binId;
    private String binType;
    private String driverName;
    private Date collectionDate;
}
