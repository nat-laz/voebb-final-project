package com.example.voebb.exception;

public class ItemStatusNotFoundException extends RuntimeException {
    public ItemStatusNotFoundException(String statusName) {
        super("Item status '" + statusName + "' was not found in the database.");
    }
}
