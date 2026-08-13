package com.careerbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.ldap.autoconfigure.LdapAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { LdapAutoConfiguration.class })
@ComponentScan(basePackages = {
		"com.careerbridge.controller",
		"com.careerbridge.service",
		"com.careerbridge.repository",
		"com.careerbridge.entity",
		"com.careerbridge.config",
		"com.careerbridge.util",
		"com.careerbridge.constants",
		"com.careerbridge.dto",
		"com.careerbridge.security",
		"com.careerbridge.exception"
})
public class CareerbridgeApplication extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CareerbridgeApplication.class);
	}

	public static void main(String[] args) {
//		System.out.println("Starting Spring Boot Application");
		SpringApplication.run(CareerbridgeApplication.class, args);
	}

}
