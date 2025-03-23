package com.microservices.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiUsuarioApplication {

	public static void main(String[] args) {

		File log4j2file = new File("src/main/resources/log4j2.properties");
		Configurator.initialize(null, log4j2file.getAbsolutePath());
		SpringApplication.run(ApiUsuarioApplication.class, args);
	}

}
