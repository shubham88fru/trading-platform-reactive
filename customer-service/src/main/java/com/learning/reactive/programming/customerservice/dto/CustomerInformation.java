package com.learning.reactive.programming.customerservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerInformation {
    private int id;
    private String name;
    private int balance;
    List<Holding> holdings;
}
