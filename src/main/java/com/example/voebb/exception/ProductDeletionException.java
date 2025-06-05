package com.example.voebb.exception;

public class ProductDeletionException extends RuntimeException {
    public ProductDeletionException(Long id, long copyCount) {
        super("Cannot delete product with ID: " + id + " it has " + copyCount + " copy(ies).");
    }
}
