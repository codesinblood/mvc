package com.objectfrontier.training.java.service.util;

public enum ErrorCode {

    FIRST_NAME_NOT_NULL("First Name cannot be empty"),
    NAME_NOT_SAME("First Name and Last Name cannot be same"),
    LAST_NAME_NOT_NULL("Last Name cannot be empty"),
    CITY_NOT_NULL("City cannot be empty"),
    STREET_NOT_NULL("Street cannot be empty"),
    EMAIL_NOT_NULL("Email cannot be null"),
    PINCODE_NOT_NULL("Pincode cannot be null"),
    PERSON_NOT_FOUND("Person does not exist"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    ID_NOT_FOUND("Person not found"),
    PERSON_ALREADY_EXISTS("Person already exists"),
    INTERNAL_SERVER_ERROR_L1("Internal server error : RH. Please try again later"),
    INTERNAL_SERVER_ERROR_L3("Internal server error : DB. Please try again later"),
    INTERNAL_SERVER_ERROR_L2("Internal server error : JC. Please try again later");

    private final String errorMsg;

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMessage() {
        return errorMsg;
    }
}
