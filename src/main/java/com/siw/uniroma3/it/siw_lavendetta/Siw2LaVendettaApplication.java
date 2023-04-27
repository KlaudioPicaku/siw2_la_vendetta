package com.siw.uniroma3.it.siw_lavendetta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class Siw2LaVendettaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Siw2LaVendettaApplication.class, args);
	}

}
