package com.learning.reactive.programming.customerservice.service;

import com.learning.reactive.programming.customerservice.dto.StockTradeRequest;
import com.learning.reactive.programming.customerservice.dto.StockTradeResponse;
import com.learning.reactive.programming.customerservice.entity.Customer;
import com.learning.reactive.programming.customerservice.entity.PortfolioItem;
import com.learning.reactive.programming.customerservice.exceptions.ApplicationExceptions;
import com.learning.reactive.programming.customerservice.mapper.EntityDtoMapper;
import com.learning.reactive.programming.customerservice.repository.CustomerRepository;
import com.learning.reactive.programming.customerservice.repository.PortfolioItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class TradeService {

    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    @Transactional
    public Mono<StockTradeResponse> trade(int customerId, StockTradeRequest stockTradeRequest) {
        return switch (stockTradeRequest.getAction()) {
            case BUY -> buyStock(customerId, stockTradeRequest);
            case  SELL -> sellStock(customerId, stockTradeRequest);
            default -> throw new IllegalStateException("Unexpected value: " + stockTradeRequest.getAction());
        };
    }

    private Mono<StockTradeResponse> sellStock(int customerId, StockTradeRequest stockTradeRequest) {
        Mono<Customer> customerMono = customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId));

        Mono<PortfolioItem> portfolioItemMono = portfolioItemRepository
                .findByCustomerIdAndTicker(customerId, stockTradeRequest.getTicker())
                .filter(pi -> pi.getQuantity() >= stockTradeRequest.getQuantity())
                .switchIfEmpty(ApplicationExceptions.insufficientShares(customerId));

        return customerMono.zipWhen(customer -> portfolioItemMono)
                .flatMap(tuple -> executeSell(tuple.getT1(),
                        tuple.getT2(), stockTradeRequest));
    }

    private Mono<StockTradeResponse> executeSell(Customer customer, PortfolioItem portfolioItem,
                                                StockTradeRequest stockTradeRequest) {
        customer.setBalance(customer.getBalance() + stockTradeRequest.totalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity() - stockTradeRequest.getQuantity());
        StockTradeResponse response = EntityDtoMapper
                .toStockTradeResponse(stockTradeRequest, customer.getId(), customer.getBalance());
        return Mono.zip(customerRepository.save(customer), portfolioItemRepository.save(portfolioItem))
                .thenReturn(response);
    }

    private Mono<StockTradeResponse> buyStock(int customerId, StockTradeRequest stockTradeRequest) {
        Mono<Customer> customerMono = customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                .filter(c -> c.getBalance() >= stockTradeRequest.totalPrice())
                .switchIfEmpty(ApplicationExceptions.insufficientBalance(customerId));

        Mono<PortfolioItem> portfolioItemMono = portfolioItemRepository.findByCustomerIdAndTicker(customerId, stockTradeRequest.getTicker())
                .defaultIfEmpty(EntityDtoMapper.toPortfolioItem(customerId, stockTradeRequest.getTicker()));

        return customerMono.zipWhen(customer -> portfolioItemMono)
                .flatMap(tuple -> executeBuy(tuple.getT1(),
                        tuple.getT2(), stockTradeRequest));


    }

    private Mono<StockTradeResponse> executeBuy(Customer customer, PortfolioItem portfolioItem,
                                                StockTradeRequest stockTradeRequest) {
        customer.setBalance(customer.getBalance() - stockTradeRequest.totalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity() + stockTradeRequest.getQuantity());
        StockTradeResponse response = EntityDtoMapper
                .toStockTradeResponse(stockTradeRequest, customer.getId(), customer.getBalance());
        return Mono.zip(customerRepository.save(customer), portfolioItemRepository.save(portfolioItem))
                .thenReturn(response);
    }
}
