package com.learning.reactive.programming.customerservice.dto;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import lombok.Data;

@Data
public class Holding {
    private Ticker ticker;
    private int quantity;
}
