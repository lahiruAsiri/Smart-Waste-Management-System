package edu.sliit.controller;

import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import edu.sliit.service.PaymentService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to manage payment-related operations.
 * It provides endpoints to add payments, fetch payment details, and retrieve the next payment date.
 */

@RestController
@Slf4j
@RequestMapping(Constants.PAYMENT_BASE_URL)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Endpoint to add a new payment.
     *
     * @param paymentDto Data Transfer Object containing the payment details to be added.
     * @return ResponseEntity with a success message or error details.
     */
    @PostMapping(Constants.PAYMENT_ADD_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createPayment(@RequestBody PaymentDto paymentDto) {
        log.info(Constants.LOG_CREATING_PAYMENT, paymentDto);
        return paymentService.createPayment(paymentDto);
    }

    /**
     * Endpoint to get all payment details for a specific user.
     *
     * @param userid The ID of the user whose payment details are being requested.
     * @return List of GetPaymentDto objects containing payment details for the specified user.
     */
    @GetMapping(Constants.PAYMENT_GET_DETAILS_URL)
    public List<GetPaymentDto> getAllPaymentsByUserId(@RequestParam String userid) {
        log.info(Constants.LOG_FETCHING_PAYMENTS, userid);
        return paymentService.getAllPaymentsByUserId(userid);
    }

    /**
     * Endpoint to get the next payment date for a specific user.
     *
     * @param userid The ID of the user whose next payment date is being requested.
     * @return The next payment date as a String for the specified user.
     */
    @GetMapping(Constants.PAYMENT_NEXT_DUE_URL)
    public String getNextPaymentDate(@RequestParam String userid) {
        log.info(Constants.LOG_FETCHING_NEXT_PAYMENT_DUE, userid);
        return paymentService.getNextPaymentDueDate(userid);
    }
}
