package com.ijse.shippingms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ShippingmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingmsApplication.class, args);
    }
}
