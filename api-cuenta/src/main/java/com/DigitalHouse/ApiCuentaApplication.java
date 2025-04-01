package com.DigitalHouse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//@ComponentScan(basePackages = {"com.microservices.cuenta", "com.DigitalHouse.common"})
public class ApiCuentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCardApplication.class, args);
	}

}
