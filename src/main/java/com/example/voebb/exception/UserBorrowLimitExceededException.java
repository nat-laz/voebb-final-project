package com.example.voebb.exception;

public class UserBorrowLimitExceededException extends RuntimeException {
    public UserBorrowLimitExceededException(Long id, String firstName, String lastName) {
        super("User with ID: [#" + id + "]" + firstName + " " + lastName + "reached the max limit of 5 items.");
    }
}
