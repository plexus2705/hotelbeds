package com.hack.detection.system.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hack.detection.system.entity.Usuario;
import com.hack.detection.system.security.impl.HackerDetectorImpl;
import com.hack.detection.system.service.impl.LoginServiceImpl;

public class HackerDetectorTest {
	
	@InjectMocks 
	private HackerDetectorImpl service;
	

	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void loginOkTest() {
		
		String line = "Prueba testing";

		String response = service.parseLine(line);
		assertEquals("OK", response);

	}

}
