package com.learning.reactive.programming.customerservice;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import com.learning.reactive.programming.customerservice.domain.TradeAction;
import com.learning.reactive.programming.customerservice.dto.StockTradeRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@SpringBootTest
@AutoConfigureWebTestClient
class CustomerServiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceApplicationTests.class);

    @Autowired
    private WebTestClient client;


    @Test
    public void customerInformation() {
        getCustomer(1, HttpStatus.OK)
                .jsonPath("$.name")
                .isEqualTo("Sam")
                .jsonPath("$.balance")
                .isEqualTo(10000)
                .jsonPath("$.holdings")
                .isEmpty();
    }

    @Test
    public void buyAndSell() {
        StockTradeRequest buyRequest = new StockTradeRequest(Ticker.GOOGLE,
                100, 5, TradeAction.BUY);
        trade(2, buyRequest, HttpStatus.OK)
                .jsonPath("$.balance")
                .isEqualTo(9500)
                .jsonPath("$.totalPrice").isEqualTo(500);

        StockTradeRequest buyRequest2 = new StockTradeRequest(Ticker.GOOGLE,
                100, 10, TradeAction.BUY);
        trade(2, buyRequest2, HttpStatus.OK)
                .jsonPath("$.balance")
                .isEqualTo(8500)
                .jsonPath("$.totalPrice").isEqualTo(1000);

        getCustomer(2, HttpStatus.OK)
                .jsonPath("$.holdings").isNotEmpty()
                .jsonPath("$.holdings.length()").isEqualTo(1)
                .jsonPath("$.holdings[0].ticker").isEqualTo(Ticker.GOOGLE)
                .jsonPath("$.holdings[0].quantity").isEqualTo(15);


        StockTradeRequest sellRequest = new StockTradeRequest(Ticker.GOOGLE,
                110, 5, TradeAction.SELL);
        trade(2, sellRequest, HttpStatus.OK)
                .jsonPath("$.balance")
                .isEqualTo(9050)
                .jsonPath("$.totalPrice").isEqualTo(550);

        StockTradeRequest sellRequest2 = new StockTradeRequest(Ticker.GOOGLE,
                110, 10, TradeAction.SELL);
        trade(2, sellRequest2, HttpStatus.OK)
                .jsonPath("$.balance")
                .isEqualTo(10150)
                .jsonPath("$.totalPrice").isEqualTo(1100);

        getCustomer(2, HttpStatus.OK)
                .jsonPath("$.holdings").isNotEmpty()
                .jsonPath("$.holdings.length()").isEqualTo(1)
                .jsonPath("$.holdings[0].ticker").isEqualTo(Ticker.GOOGLE)
                .jsonPath("$.holdings[0].quantity").isEqualTo(0);

    }

    @Test
    public void customerNotFound() {
        getCustomer(10, HttpStatus.NOT_FOUND)
                .jsonPath("$.detail").isEqualTo("Customer [id=10] not found");

        StockTradeRequest sellRequest = new StockTradeRequest(Ticker.GOOGLE,
                110, 10, TradeAction.SELL);
        trade(20, sellRequest, HttpStatus.NOT_FOUND)
                .jsonPath("$.detail").isEqualTo("Customer [id=20] not found");

    }

    @Test
    public void insufficientBalance() {
        StockTradeRequest buyRequest = new StockTradeRequest(Ticker.GOOGLE,
                100, 101, TradeAction.BUY);
        trade(3, buyRequest, HttpStatus.BAD_REQUEST)
                .jsonPath("$.detail")
                .isEqualTo("Customer [id=3] doesn't have sufficient funds to complete the transaction");

    }

    @Test
    public void insufficientShares() {
        StockTradeRequest sellRequest = new StockTradeRequest(Ticker.GOOGLE,
                110, 1000, TradeAction.SELL);
        trade(1, sellRequest, HttpStatus.BAD_REQUEST)
                .jsonPath("$.detail").isEqualTo("Customer [id=1] doesn't have sufficient shares to complete the transaction");
    }

    private WebTestClient.BodyContentSpec getCustomer(int customerId, HttpStatus expectedStatus) {
        return this.client.get()
                .uri("/customers/{customerId}", customerId)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects
                        .requireNonNull(r.getResponseBody()))));
    }

    private WebTestClient.BodyContentSpec trade(int customerId, StockTradeRequest tradeRequest,
                                                HttpStatus expectedStatus) {
        return this.client.post()
                .uri("/customers/{customerId}/trade", customerId)
                .bodyValue(tradeRequest)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects
                        .requireNonNull(r.getResponseBody()))));
    }

}
