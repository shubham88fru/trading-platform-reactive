package com.learning.reactive.programming.customerservice.mapper;

import com.learning.reactive.programming.customerservice.dto.CustomerInformation;
import com.learning.reactive.programming.customerservice.dto.Holding;
import com.learning.reactive.programming.customerservice.entity.Customer;
import com.learning.reactive.programming.customerservice.entity.PortfolioItem;

import java.util.List;

//mapper
public class EntityDtoMapper {

    public static CustomerInformation
        toCustomerInformation(Customer customer, List<PortfolioItem> items) {

        List<Holding> holdings = items.stream()
                .map(i -> new Holding(i.getTicker(), i.getQuantity()))
                .toList();

        return new CustomerInformation(
                customer.getId(),
                customer.getName(),
                customer.getBalance(),
                holdings
        );
    }
}
