package com.learning.reactive.programming.customerservice.repository;

import com.learning.reactive.programming.customerservice.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}
