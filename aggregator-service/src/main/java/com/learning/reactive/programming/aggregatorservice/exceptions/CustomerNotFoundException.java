package com.learning.reactive.programming.aggregatorservice.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Customer [id=%d] not found";

    public CustomerNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }
}
