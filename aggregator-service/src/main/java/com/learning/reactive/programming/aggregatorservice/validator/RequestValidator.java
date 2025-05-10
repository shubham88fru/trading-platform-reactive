package com.learning.reactive.programming.aggregatorservice.validator;

import com.learning.reactive.programming.aggregatorservice.dto.TradeRequest;
import com.learning.reactive.programming.aggregatorservice.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<TradeRequest>> validate() {
        return mono -> mono.filter(hasTicker())
                .switchIfEmpty(ApplicationExceptions.missingTicker())
                .filter(hasTradeAction())
                .switchIfEmpty(ApplicationExceptions.missingTradeAction())
                .filter(isValidQuantity())
                .switchIfEmpty(ApplicationExceptions.invalidQuantity());
    }

    private static Predicate<TradeRequest> hasTicker() {
        return dto -> Objects.nonNull(dto.getTicker());
    }


    private static Predicate<TradeRequest> hasTradeAction() {
        return dto -> Objects.nonNull(dto.getTradeAction());
    }


    private static Predicate<TradeRequest> isValidQuantity() {
        return dto -> Objects.nonNull(dto.getQuantity()) && dto.getQuantity() > 0;
    }
}

