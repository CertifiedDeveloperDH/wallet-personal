package com.DigitalHouse;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiCuentaApplication {

	public static void main(String[] args) {

		File log4jfile = new File("src/main/resources/log4j.properties");
		PropertyConfigurator.configure(log4jfile.getAbsolutePath());
		SpringApplication.run(ApiCuentaApplication.class, args);
	}

}
