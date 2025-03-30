package com.example.abyad;

import com.example.abyad.AbyadExceptions.AbyadErrorMapping;
import com.example.abyad.Schemas.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbyadApplicationTests {

	@Autowired
	Properties properties;
	@Test
	void contextLoads() {
		System.out.println(properties);
	}

}
