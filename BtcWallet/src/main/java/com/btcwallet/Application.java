package com.btcwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableCaching
@EnableTransactionManagement(proxyTargetClass=true)
public class Application {
	
	public static void main(String[] args) throws Exception {
		
		SpringApplication.run(Application.class, args);
	}
}
