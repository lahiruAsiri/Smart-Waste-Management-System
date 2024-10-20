package edu.sliit.util;

/**
 * This class contains constant values used throughout the application.
 * It includes message constants for logging, error handling, success messages,
 * and HTTP status codes.
 */
public class Constants {

    // User-related constants

    public static final String USERS_BASE_URL = "users";
    public static final String USER_REGISTER_URL = "register";
    public static final String USER_LIST_URL = "list";
    public static final String USER_FIND_BY_ID_URL = "findById";
    public static final String USER_STATUS_URL = "status";
    public static final String USER_POINTS_URL = "points";
    public static final String USER_ID_PREFIX = "USER";
    public static final String USER_ADDED_SUCCESS = "User added successfully with ID: ";
    public static final String USER_NOT_FOUND = "User not found with ID: ";
    public static final String STATUS_UPDATED_SUCCESS = "Status updated successfully for User ID: ";
    public static final String POINTS_UPDATED_SUCCESS = "Points updated successfully for User ID: ";
    public static final String NO_USERS_FOUND_CREDENTIALS = "No users found with the provided credentials";
    public static final String NO_USERS_FOUND_USERNAME = "No users found with username: ";

    public static final String USERS_ALREADY = "already exists Emails Or Password: ";

    // Payment-related constants
    public static final String BIN_ID_PREFIX = "BIN";
    public static final String PAY_ID_PREFIX = "PAY";

    public static final String PAYMENT_BASE_URL = "Payment";
    public static final String PAYMENT_ADD_URL = "addpayment";
    public static final String PAYMENT_GET_DETAILS_URL = "getdetails";
    public static final String PAYMENT_NEXT_DUE_URL = "nextPayment";
    public static final String PAYMENT_STATUS = "Active";
    public static final String PAYMENT_ADDED_SUCCESS = "Payment added successfully with ID: ";
    public static final String PAYMENT_NOT_FOUND = "Payment not found with ID: ";
    public static final String NO_PAYMENT_FOUND_CREDENTIALS = "No Payment found with the provided credentials";

    // Bin-related constants
    public static final String STATUS_BIN = "ISEMPTY";
    public static final String BIN_BASE_URL = "Bin";
    public static final String BIN_ADD_URL = "addbin";
    public static final String BIN_GET_DETAILS_URL = "getbindetails";
    public static final String BIN_ADDED_SUCCESS = "Bin added successfully with ID: ";
    public static final String BIN_NOT_FOUND = "Bin not found with ID: ";
    public static final String BIN_STATUS_UPDATED_SUCCESS = "Bin status updated successfully for ID: ";

    // Collection-related constants
    public static final String COLLECTION_ADDED_SUCCESS = "Collection added successfully with ID: ";

    // Error messages
    public static final String INVALID_DATA_PROVIDED = "Invalid data provided: ";
    public static final String ENTITY_NOT_FOUND = "Entity not found: ";
    public static final String DATABASE_ERROR = "Database error: ";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error: ";

    // HTTP status codes
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;

    // Increment values
    public static final long COLLECTOR_ID_INCREMENT = 1L;
    public static final long BIN_ID_INCREMENT = 1L;

    // Logging messages
    public static final String LOG_CREATING_PAYMENT = "Creating payment for user with ID: ";
    public static final String LOG_FETCHING_PAYMENTS = "Fetching all payments for userId: ";
    public static final String LOG_FETCHING_NEXT_PAYMENT_DUE = "Fetching next payment due date for userId: ";
    public static final String LOG_REGISTERING_USER = "Registering new user: [{}]";
    public static final String LOG_FETCHING_USERS_BY_CREDENTIALS = "Fetching users by credentials: username [{}]";
    public static final String LOG_FETCHING_USER_BY_ID = "Fetching user by ID: [{}]";
    public static final String LOG_FETCHING_STATUS_FOR_USER = "Fetching status for user ID: [{}]";
    public static final String LOG_FETCHING_POINTS_FOR_USER = "Fetching points for user ID: [{}]";
    public static final String LOG_CREATING_BIN = "Creating bin for [{}]";
    public static final String LOG_FETCHING_BIN_DETAILS = "Fetching bin details for userId: [{}]";


}
