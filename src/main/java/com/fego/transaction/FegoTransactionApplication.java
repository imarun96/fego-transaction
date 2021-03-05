package com.fego.transaction;

import com.fego.transaction.common.base.BaseRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Arun Balaji Rajasekaran
 */

@SpringBootApplication(scanBasePackages = "com.fego")
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class FegoTransactionApplication {

    private static final Logger logger = LoggerFactory.getLogger(FegoTransactionApplication.class);

    public static void main(String[] args) {
        new SpringApplication(FegoTransactionApplication.class).run();
        logger.info("Fego Transaction Service has been started.");
    }
}