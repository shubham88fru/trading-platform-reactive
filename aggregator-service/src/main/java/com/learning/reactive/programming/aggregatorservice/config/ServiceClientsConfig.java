package com.learning.reactive.programming.aggregatorservice.config;

import com.learning.reactive.programming.aggregatorservice.client.CustomerServiceClient;
import com.learning.reactive.programming.aggregatorservice.client.StockServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceClientsConfig {

    private static final Logger log = LoggerFactory.getLogger(ServiceClientsConfig.class);

    @Bean
    public CustomerServiceClient customerServiceClient(@Value("${customer.service.url}") String baseUrl) {
        return new CustomerServiceClient(
                createWebClient(baseUrl)
        );
    }

    @Bean
    public StockServiceClient stockServiceClient(@Value("${stock.service.url}") String baseUrl) {
        return new StockServiceClient(
                createWebClient(baseUrl)
        );
    }

    private WebClient createWebClient(String baseUrl) {

        log.info("Base url: {}", baseUrl);

        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
