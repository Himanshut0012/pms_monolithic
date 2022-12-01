package com.josh.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.josh.*")
@EntityScan(basePackages = {"com.josh.*"})
@EnableJpaRepositories(basePackages = {"com.josh.*"})
@EnableScheduling
public class PMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(PMSApplication.class);
    }
}
