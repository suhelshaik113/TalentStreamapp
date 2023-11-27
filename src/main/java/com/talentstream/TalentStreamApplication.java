package com.talentstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@SpringBootApplication
public class TalentStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentStreamApplication.class, args);
	}

}
