package com.learning.reactive.programming.aggregatorservice.controller;

import com.learning.reactive.programming.aggregatorservice.client.StockServiceClient;
import com.learning.reactive.programming.aggregatorservice.dto.PriceUpdate;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@RequestMapping("stock")
public class StockPriceStreamController {

    private final StockServiceClient stockServiceClient;

    @GetMapping(value = "/price-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PriceUpdate> priceUpdateStream() {
        return stockServiceClient.priceUpdateStream();
    }
}
