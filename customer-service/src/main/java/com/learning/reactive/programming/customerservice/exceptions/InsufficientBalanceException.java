package com.learning.reactive.programming.customerservice.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    private static final String MESSAGE = "Customer [id=%d] doesn't have sufficient funds to complete the transaction";

    public InsufficientBalanceException(int customerId) {
        super(MESSAGE.formatted(customerId));
    }
}
