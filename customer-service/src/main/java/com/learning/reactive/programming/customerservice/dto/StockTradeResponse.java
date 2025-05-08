package com.learning.reactive.programming.customerservice.dto;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import com.learning.reactive.programming.customerservice.domain.TradeAction;
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
