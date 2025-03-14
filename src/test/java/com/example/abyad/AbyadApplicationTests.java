package com.example.abyad;

import com.example.abyad.AbyadExceptions.AbyadErrorMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbyadApplicationTests {

	@Autowired
    AbyadErrorMapping abyadProperties;
	@Test
	void contextLoads() {
		System.out.println(abyadProperties);
	}

}
