package com.bootcamp.credit.consumption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CreditConsumptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditConsumptionApplication.class, args);
	}

}
