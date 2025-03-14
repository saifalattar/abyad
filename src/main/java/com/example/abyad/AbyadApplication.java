package com.example.abyad;

import com.example.abyad.AbyadExceptions.AbyadErrorMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AbyadErrorMapping.class})
public class AbyadApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbyadApplication.class, args);
	}
}
