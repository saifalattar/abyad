package com.example.abyad;

import com.example.abyad.AbyadExceptions.AbyadErrorMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({AbyadErrorMapping.class, Properties.class})
public class AbyadApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbyadApplication.class, args);
	}
}