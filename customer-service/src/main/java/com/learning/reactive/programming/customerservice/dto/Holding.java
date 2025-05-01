package com.learning.reactive.programming.customerservice.dto;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Holding {
    private Ticker ticker;
    private int quantity;
}
