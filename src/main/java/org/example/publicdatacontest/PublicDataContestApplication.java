package org.example.publicdatacontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PublicDataContestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicDataContestApplication.class, args);
    }

}
