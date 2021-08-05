package com.thiago.dscatalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.thiago.dscatalog.resources.UserResource;

@SpringBootTest
public class SmokeTest {
	
	@Autowired
	UserResource resource;
	
	@Test
	public void contextLoads() throws Exception {
		assertNotNull(resource);
	}

}
