package edu.sliit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {
    private String PaymentId;
    private String UserId;
    private Number PaymentAmount;
    private Date PaymentDate;
    private Date NextPaymentDate;
}
