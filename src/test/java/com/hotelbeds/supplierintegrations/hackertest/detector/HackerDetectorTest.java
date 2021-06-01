package com.hotelbeds.supplierintegrations.hackertest.detector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.hotelbeds.supplierintegrations.hackertest.detector.impl.HackerDetectorImpl;

public class HackerDetectorTest {

	@InjectMocks
	private HackerDetectorImpl service;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void parseLineIpSospechosaTest() {
		
		Date currentDate = new Date();
		long epoch = currentDate.getTime() / 1000;
		String response = null;
		String line = "80.238.9.179,"+epoch+",SIGNIN_FAILURE,Will.Smith";
		

		for(int i = 0 ; i<6;i++) {
			response = service.parseLine(line);
		}
		assertEquals("IP sospechosa", response);
	}
	
	@Test
	public void parseLineIpNoSospechosaTest() {
		
		Date currentDate = new Date();
		long epoch = currentDate.getTime() / 1000;
		String response = null;
		String line = "80.238.9.179,"+epoch+",SIGNIN_FAILURE,Will.Smith";
		

		for(int i = 0 ; i<3;i++) {
			response = service.parseLine(line);
		}
		assertNull(response);
	}
	
}
