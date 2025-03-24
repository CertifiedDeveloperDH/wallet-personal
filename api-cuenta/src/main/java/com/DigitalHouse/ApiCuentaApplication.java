package com.DigitalHouse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiCuentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCuentaApplication.class, args);
	}

}
