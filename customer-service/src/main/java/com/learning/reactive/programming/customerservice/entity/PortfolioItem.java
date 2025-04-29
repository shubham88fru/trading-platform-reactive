package com.learning.reactive.programming.customerservice.entity;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PortfolioItem {

    @Id
    private int id;
    private int customerId;
    private Ticker ticker;
    private int quantity;
}
