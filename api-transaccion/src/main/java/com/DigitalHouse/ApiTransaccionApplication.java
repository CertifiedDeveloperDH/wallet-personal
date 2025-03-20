package com.DigitalHouse;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.File;

@SpringBootApplication
@EnableFeignClients
public class ApiTransaccionApplication {

	public static void main(String[] args) {

		File log4jfile = new File("src/main/resources/log4j.properties");
		PropertyConfigurator.configure(log4jfile.getAbsolutePath());
		SpringApplication.run(ApiTransaccionApplication.class, args);
	}

}
