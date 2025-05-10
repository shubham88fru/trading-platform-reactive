package com.learning.reactive.programming.aggregatorservice.dto;

import com.learning.reactive.programming.aggregatorservice.domain.Ticker;
import com.learning.reactive.programming.aggregatorservice.domain.TradeAction;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockTradeResponse {
    private int customerId;
    private Ticker ticker;
    private int price;
    private int quantity;
    private TradeAction action;
    private int totalPrice;
    private int balance;
}
