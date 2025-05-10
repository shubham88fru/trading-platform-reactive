package com.learning.reactive.programming.aggregatorservice.dto;

import com.learning.reactive.programming.aggregatorservice.domain.Ticker;
import com.learning.reactive.programming.aggregatorservice.domain.TradeAction;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StockTradeRequest {
    private Ticker ticker;
    private int price;
    private int quantity;
    private TradeAction action;

}
