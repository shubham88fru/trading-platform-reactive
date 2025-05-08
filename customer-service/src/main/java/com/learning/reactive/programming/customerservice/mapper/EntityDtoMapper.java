package com.learning.reactive.programming.customerservice.mapper;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import com.learning.reactive.programming.customerservice.dto.CustomerInformation;
import com.learning.reactive.programming.customerservice.dto.Holding;
import com.learning.reactive.programming.customerservice.dto.StockTradeRequest;
import com.learning.reactive.programming.customerservice.dto.StockTradeResponse;
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

    public static PortfolioItem toPortfolioItem(int customerId, Ticker ticker) {
        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setCustomerId(customerId);
        portfolioItem.setTicker(ticker);
        portfolioItem.setQuantity(0);
        return portfolioItem;
    }

    public static StockTradeResponse toStockTradeResponse(StockTradeRequest request, int customerId, int balance) {
        return new StockTradeResponse(
                customerId,
                request.getTicker(),
                request.getPrice(),
                request.getQuantity(),
                request.getAction(),
                request.totalPrice(),
                balance
        );
    }
}
