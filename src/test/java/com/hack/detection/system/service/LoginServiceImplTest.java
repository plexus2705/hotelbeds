package com.hack.detection.system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hack.detection.system.entity.Usuario;
import com.hack.detection.system.security.HackerDetector;
import com.hack.detection.system.service.impl.LoginServiceImpl;

public class LoginServiceImplTest {

	@InjectMocks 
	private LoginServiceImpl service;
	
	@Mock
	private HackerDetector hackerDetector;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void loginOkTest() {
		
		String ip = "127.0.0.1";
		String user = "admin";
		String pass = "1234";
		
		
		Mockito.when(hackerDetector.parseLine(Mockito.anyString())).thenReturn("");
		Usuario response = service.login(ip, user, pass);
		assertEquals(1, response.getId());
		assertEquals("admin", response.getNombre());
	}
	
	@Test
	public void loginNoOkTest() {
		
		String ip = "127.0.0.1";
		String user = "admin";
		String pass = "123";
		
		
		Mockito.when(hackerDetector.parseLine(Mockito.anyString())).thenReturn("");
		Usuario response = service.login(ip, user, pass);
		assertNull(response);

	}
	
	
}
