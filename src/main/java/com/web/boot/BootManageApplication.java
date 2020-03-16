package com.web.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class BootManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootManageApplication.class, args);
	}

}
