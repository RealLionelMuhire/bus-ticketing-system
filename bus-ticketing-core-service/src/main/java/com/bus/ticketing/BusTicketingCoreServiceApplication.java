package com.bus.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BusTicketingCoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusTicketingCoreServiceApplication.class, args);
    }
}