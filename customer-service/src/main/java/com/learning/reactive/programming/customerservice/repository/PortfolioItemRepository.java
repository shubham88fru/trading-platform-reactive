package com.learning.reactive.programming.customerservice.repository;

import com.learning.reactive.programming.customerservice.domain.Ticker;
import com.learning.reactive.programming.customerservice.entity.PortfolioItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PortfolioItemRepository extends ReactiveCrudRepository<PortfolioItem, Integer> {
    Flux<PortfolioItem> findAllByCustomerId(int customerId);
    Mono<PortfolioItem> findByCustomerIdAndTicker(int customerId, Ticker ticker);
}
