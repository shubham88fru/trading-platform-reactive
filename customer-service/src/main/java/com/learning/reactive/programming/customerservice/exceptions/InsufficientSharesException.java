package com.learning.reactive.programming.customerservice.exceptions;

public class InsufficientSharesException extends RuntimeException {
    private static final String MESSAGE = "Customer [id=%d] doesn't have sufficient shares to complete the transaction";

    public InsufficientSharesException(int customerId) {
        super(MESSAGE.formatted(customerId));
    }
}

