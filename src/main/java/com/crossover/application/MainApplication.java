package com.crossover.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * This is main class of this project
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see SpringBootApplication
 * @see ComponentScan
 * @see EnableMongoRepositories
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.crossover.*"})
@EnableMongoRepositories(basePackages = {"com.crossover.*"})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
