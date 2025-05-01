package com.learning.reactive.programming.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomerInformation {
    private int id;
    private String name;
    private int balance;
    List<Holding> holdings;
}
