package com.learning.reactive.programming.customerservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {

    @Id
    private int id;
    private String name;
    private int balance;
}
