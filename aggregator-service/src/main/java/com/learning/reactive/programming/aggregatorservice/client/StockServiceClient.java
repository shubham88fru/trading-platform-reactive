package com.learning.reactive.programming.aggregatorservice.client;

import com.learning.reactive.programming.aggregatorservice.domain.Ticker;
import com.learning.reactive.programming.aggregatorservice.dto.PriceUpdate;
import com.learning.reactive.programming.aggregatorservice.dto.StockPriceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

public class StockServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceClient.class);

    private final WebClient client;
    private Flux<PriceUpdate> flux;

    public StockServiceClient(WebClient client) {
        this.client = client;
    }

    public Mono<StockPriceResponse> getStockPrice(Ticker ticker) {
        return this.client.get()
                .uri("/stock/{ticker}")
                .retrieve()
                .bodyToMono(StockPriceResponse.class);
    }

    public Flux<PriceUpdate> priceUpdateStream() {
        if (Objects.isNull(this.flux)) {
            this.flux = this.getPriceUpdates();
        }
        return this.flux;
    }

    public Flux<PriceUpdate> getPriceUpdates() {
        return this.client.get()
                .uri("/sock/price-stream")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(PriceUpdate.class)
                .retryWhen(retry())
                .cache(1);
    }

    private Retry retry() {
        return Retry.fixedDelay(100, Duration.ofSeconds(1))
                .doBeforeRetry(rs -> logger.error("stock service price " +
                        "stream call failed. retrying {}", rs.failure().getMessage()));
    }

}
