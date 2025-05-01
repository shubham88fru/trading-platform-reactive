package com.learning.reactive.programming.customerservice.service;

import com.learning.reactive.programming.customerservice.dto.CustomerInformation;
import com.learning.reactive.programming.customerservice.entity.Customer;
import com.learning.reactive.programming.customerservice.exceptions.ApplicationExceptions;
import com.learning.reactive.programming.customerservice.mapper.EntityDtoMapper;
import com.learning.reactive.programming.customerservice.repository.CustomerRepository;
import com.learning.reactive.programming.customerservice.repository.PortfolioItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public Mono<CustomerInformation> getCustomerInformation(int customerId) {
        return this.customerRepository.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(customerId))
                .flatMap(this::buildCustomerInformation);
    }

    private Mono<CustomerInformation> buildCustomerInformation(Customer customer) {
       return this.portfolioItemRepository.findAllByCustomerId(customer.getId())
               .collectList()
               .map(list ->
                       EntityDtoMapper.toCustomerInformation(customer, list));
    }

}
