package com.backbone.phalanx;

import org.springframework.boot.SpringApplication;

public class TestPhalanxApplication {

	public static void main(String[] args) {
		SpringApplication.from(PhalanxApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
