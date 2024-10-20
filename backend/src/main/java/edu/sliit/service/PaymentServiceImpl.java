package edu.sliit.service;

import edu.sliit.config.ModelMapperSingleton;
import edu.sliit.document.Payment;
import edu.sliit.document.User;
import edu.sliit.dto.GetPaymentDto;
import edu.sliit.dto.PaymentDto;
import edu.sliit.repository.PaymentRepository;
import edu.sliit.repository.UserRepository;
import edu.sliit.service.PaymentService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the PaymentService interface.
 * Provides methods to manage payment-related operations, such as creating,
 * and retrieving payment, while handling various exceptions related to data validation
 * and database interactions.
 *
 * This service includes methods for:
 * - Adding a Payment
 * - Fetching Payment by credentials or userid
 * - Retrieving Payment NextPaymentDate
 *
 * @see PaymentService
 * @see Payment
 * @see PaymentRepository
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = ModelMapperSingleton.getInstance();


    /**
     * Creates a new payment by generating a unique payment ID and saving the payment data.
     * into the repository. Returns a success message upon successful creation.
     *
     * @param paymentDto The payment details provided by the client.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If invalid data is provided.
     * @throws EntityNotFoundException If an entity is not found during the operation.
     *  @throws Exception If an unexpected error occurs.
     */
    @Override
    public ResponseEntity<String> createPayment(PaymentDto paymentDto) {
        try {
            log.info(Constants.LOG_CREATING_PAYMENT + paymentDto.getUserId());
            log.info(Constants.LOG_CREATING_PAYMENT + paymentDto.getPaymentDate());
            // Check if userId is null before proceeding
            if (paymentDto.getUserId() == null) {
                log.error(Constants.INVALID_DATA_PROVIDED + " UserId is null.");
                return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + " UserId must not be null.");
            }

            // Find user and update status
            Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserId(paymentDto.getUserId()));
            if (userOptional.isEmpty()) {
                log.error(Constants.USER_NOT_FOUND + paymentDto.getUserId());
                return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.USER_NOT_FOUND + paymentDto.getUserId());
            }

            User user = userOptional.get();
            user.setStatus(Constants.PAYMENT_STATUS);
            userRepository.save(user);
            log.info(Constants.STATUS_UPDATED_SUCCESS + paymentDto.getUserId());

            Date paymentDate = paymentDto.getPaymentDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(paymentDate);
            calendar.add(Calendar.MONTH, 1); // 1 month increment
            Date nextPaymentDate = calendar.getTime();

            long paymentCount = paymentRepository.count();
            String generatedPaymentId = Constants.PAY_ID_PREFIX + (paymentCount + 1);
            Payment paymentEntity = modelMapper.map(paymentDto, Payment.class);

            paymentEntity.setPaymentId(generatedPaymentId); // Incrementing ID
            paymentEntity.setNextPaymentDate(nextPaymentDate);
            paymentEntity.setId(new ObjectId().toString());

            paymentRepository.save(paymentEntity);
            log.info(Constants.PAYMENT_ADDED_SUCCESS + paymentEntity.getPaymentId());

            return ResponseEntity.ok(Constants.PAYMENT_ADDED_SUCCESS + paymentEntity.getPaymentId());
        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED + ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.USER_NOT_FOUND + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.USER_NOT_FOUND + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR).body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }


    /**
     * Retrieves a list of payments that match the provided userid .
     * Converts each User entity into a UserPaymentDto.
     *
     * @param userid The userid to match.
     * @return List<GetPaymentDto> A list of matching payments.
     * @throws EntityNotFoundException If no users are found with the provided credentials.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public List<GetPaymentDto> getAllPaymentsByUserId(String userid) {
        try {
            log.info(Constants.LOG_FETCHING_PAYMENTS + userid);
            List<Payment> payments = paymentRepository.findByUserId(userid);
            return payments.stream()
                    .map(payment -> modelMapper.map(payment, GetPaymentDto.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.PAYMENT_NOT_FOUND + userid, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Retrieves the next payment due date for a user based on their userId.
     *
     * @param userid The ID of the payment whose NextPaymentDate is being retrieved.
     * @return String The NextPaymentDate of the user, or an error message if not found.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public String getNextPaymentDueDate(String userid) {
        try {
            log.info(Constants.LOG_FETCHING_NEXT_PAYMENT_DUE + userid);
            // Fetch all payment records for the user
            List<Payment> payments = paymentRepository.findByUserId(userid);

            // Check if any payments exist for the user
            if (payments.isEmpty()) {
                log.error(Constants.PAYMENT_NOT_FOUND + userid);
                throw new EntityNotFoundException(Constants.PAYMENT_NOT_FOUND + userid);
            }

            // Get the current date
            Date currentDate = new Date();

            // Find the payment with the closest next payment date to the current date
            Payment closestPayment = payments.stream()
                    .min((p1, p2) -> {
                        long diff1 = Math.abs(p1.getNextPaymentDate().getTime() - currentDate.getTime());
                        long diff2 = Math.abs(p2.getNextPaymentDate().getTime() - currentDate.getTime());
                        return Long.compare(diff1, diff2);
                    })
                    .orElseThrow(() -> new EntityNotFoundException(Constants.PAYMENT_NOT_FOUND + userid));

            // Return the closest payment's next payment date
            return closestPayment.getNextPaymentDate().toString();

        } catch (EntityNotFoundException ex) {
            log.error(Constants.PAYMENT_NOT_FOUND + userid, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }

}
