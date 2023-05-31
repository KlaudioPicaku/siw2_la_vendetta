package com.siw.uniroma3.it.siw_lavendetta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@EnableJpaRepositories("com.siw.uniroma3.it.siw_lavendetta.repositories")
@EntityScan("com.siw.uniroma3.it.siw_lavendetta.models")
//@ComponentScan(basePackages = "com.siw.uniroma3.it.siw_lavendetta")
@SpringBootApplication()

public class Siw2LaVendettaApplication {
	public static void main(String[] args) {
		SpringApplication.run(Siw2LaVendettaApplication.class, args);
	}

}