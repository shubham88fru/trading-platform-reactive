package com.learning.reactive.programming.aggregatorservice.exceptions;

public class InvalidTradeRequestException extends RuntimeException {
    public InvalidTradeRequestException(String message) {
        super(message);
    }
}
