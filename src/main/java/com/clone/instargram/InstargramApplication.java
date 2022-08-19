package com.clone.instargram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InstargramApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstargramApplication.class, args);
    }

}
