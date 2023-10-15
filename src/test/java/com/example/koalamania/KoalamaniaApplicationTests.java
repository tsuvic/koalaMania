package com.example.koalamania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KoalamaniaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testAdd() {
        assertEquals(3, 1+2);
    }

}
