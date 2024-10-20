package edu.sliit.service;


import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {
    ResponseEntity<String> createPayment(PaymentDto dto);
    List<GetPaymentDto> getAllPaymentsByUserId (String userid);
    String getNextPaymentDueDate(String userid);
}
