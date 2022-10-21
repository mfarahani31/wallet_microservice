package com.leovegas.walletmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WalletMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletMicroserviceApplication.class, args);
    }

}
