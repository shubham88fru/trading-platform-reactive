package com.learning.reactive.programming.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerInformation {
    private int id;
    private String name;
    private int balance;
    List<Holding> holdings;
}
