package com.learning.reactive.programming.aggregatorservice.controller;

import com.learning.reactive.programming.aggregatorservice.dto.CustomerInformation;
import com.learning.reactive.programming.aggregatorservice.dto.StockTradeResponse;
import com.learning.reactive.programming.aggregatorservice.dto.TradeRequest;
import com.learning.reactive.programming.aggregatorservice.service.CustomerPortfolioService;
import com.learning.reactive.programming.aggregatorservice.validator.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("customers")
public class CustomerPortfolioController {

    private final CustomerPortfolioService customerPortfolioService;

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable int customerId) {
        return customerPortfolioService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable int customerId, @RequestBody Mono<TradeRequest> mono) {
        return mono.transform(RequestValidator.validate())
                .flatMap(req -> customerPortfolioService.trade(customerId, req));
    }
}
