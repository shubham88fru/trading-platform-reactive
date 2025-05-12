package com.learning.reactive.programming.aggregatorservice.service;

import com.learning.reactive.programming.aggregatorservice.client.CustomerServiceClient;
import com.learning.reactive.programming.aggregatorservice.client.StockServiceClient;
import com.learning.reactive.programming.aggregatorservice.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerPortfolioService {

    private final StockServiceClient stockServiceClient;
    private final CustomerServiceClient customerServiceClient;

    public Mono<CustomerInformation> getCustomerInformation(int customerId) {
        return this.customerServiceClient.getCustomerInformation(customerId);
    }

    public Mono<StockTradeResponse> trade(int customerId, TradeRequest request) {
        return this.stockServiceClient.getStockPrice(request.getTicker())
                .map(StockPriceResponse::getPrice)
                .map(price -> toStockTradeRequest(request, price))
                .flatMap(req -> customerServiceClient.trade(customerId, req));
    }

    private StockTradeRequest toStockTradeRequest(TradeRequest request, int price) {
        return new StockTradeRequest(
                request.getTicker(),
                price,
                request.getQuantity(),
                request.getTradeAction()
        );
    }

}
