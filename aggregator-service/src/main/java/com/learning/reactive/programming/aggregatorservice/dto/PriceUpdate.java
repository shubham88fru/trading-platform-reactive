package com.learning.reactive.programming.aggregatorservice.dto;

import com.learning.reactive.programming.aggregatorservice.domain.Ticker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PriceUpdate {
    private Ticker ticker;
    private int price;
    private LocalDateTime time;
}
