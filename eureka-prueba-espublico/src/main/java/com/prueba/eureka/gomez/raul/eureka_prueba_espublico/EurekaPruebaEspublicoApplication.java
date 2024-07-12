package com.prueba.eureka.gomez.raul.eureka_prueba_espublico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaPruebaEspublicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaPruebaEspublicoApplication.class, args);
	}

}
