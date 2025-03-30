package com.DigitalHouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.DigitalHouse.common.exception"})
public class ApiTransaccionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTransaccionApplication.class, args);
	}

}
