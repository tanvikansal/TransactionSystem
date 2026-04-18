package com.visa.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.visa.transaction")
public class TransactionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionSystemApplication.class, args);
    }

}
