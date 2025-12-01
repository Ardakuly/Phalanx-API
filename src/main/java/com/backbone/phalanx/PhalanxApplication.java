package com.backbone.phalanx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PhalanxApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhalanxApplication.class, args);
	}

}
