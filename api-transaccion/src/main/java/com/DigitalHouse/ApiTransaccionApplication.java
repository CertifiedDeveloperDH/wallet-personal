package com.DigitalHouse;

import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

@SpringBootApplication
@EnableFeignClients
public class ApiTransaccionApplication {

	public static void main(String[] args) {

		File log4j2file = new File("src/main/resources/log4j2.properties");
		Configurator.initialize(null, log4j2file.getAbsolutePath());
		SpringApplication.run(ApiTransaccionApplication.class, args);
	}

}
