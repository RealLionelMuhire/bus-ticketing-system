package com.bus.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BusTicketingServiceRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusTicketingServiceRegistryApplication.class, args);
    }
}