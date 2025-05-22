package com.example.voebb.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Item with ID: [#" + id + "] was not found.");
    }
}
