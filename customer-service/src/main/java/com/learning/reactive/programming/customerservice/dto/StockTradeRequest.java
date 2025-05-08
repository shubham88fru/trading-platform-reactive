package com.learning.reactive.programming.customerservice.dto;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import com.learning.reactive.programming.customerservice.domain.TradeAction;
import lombok.Data;

@Data
public class StockTradeRequest {
    private Ticker ticker;
    private int price;
    private int quantity;
    private TradeAction action;

    public int totalPrice() {
        return price * quantity;
    }
}
