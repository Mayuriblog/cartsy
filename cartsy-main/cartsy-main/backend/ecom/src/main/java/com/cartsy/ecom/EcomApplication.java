package com.cartsy.ecom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication 
public class EcomApplication {
	final static Logger logger = LoggerFactory.getLogger(EcomApplication.class);

	public static void main(String[] args) {
		
		logger.info("Hello , we are starting up the ecommerce application cartsy!");
		SpringApplication.run(EcomApplication.class, args);
	}

	

}
