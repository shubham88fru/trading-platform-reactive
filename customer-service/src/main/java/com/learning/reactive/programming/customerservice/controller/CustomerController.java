package com.learning.reactive.programming.customerservice.controller;

import com.learning.reactive.programming.customerservice.dto.CustomerInformation;
import com.learning.reactive.programming.customerservice.dto.StockTradeRequest;
import com.learning.reactive.programming.customerservice.dto.StockTradeResponse;
import com.learning.reactive.programming.customerservice.service.CustomerService;
import com.learning.reactive.programming.customerservice.service.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;
    private final TradeService tradeService;

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable int customerId) {
        return customerService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable int customerId,
                                          @RequestBody Mono<StockTradeRequest> stockTradeRequest) {
        return stockTradeRequest
                .flatMap(r -> tradeService.trade(customerId, r));
    }
}
