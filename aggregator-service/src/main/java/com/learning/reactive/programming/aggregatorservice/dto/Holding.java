package com.learning.reactive.programming.aggregatorservice.dto;

import com.learning.reactive.programming.aggregatorservice.domain.Ticker;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Holding {
    private Ticker ticker;
    private int quantity;
}
