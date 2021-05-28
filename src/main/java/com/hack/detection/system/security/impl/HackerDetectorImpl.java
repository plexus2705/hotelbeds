package com.hack.detection.system.security.impl;



import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.stereotype.Service;

import com.hack.detection.system.security.HackerDetector;

@Service
public class HackerDetectorImpl implements HackerDetector{

	Logger log;
	
	@Override
	public String parseLine(String line) {
		String response = "";
		log = Logger.getLogger("HackerDetectorLog");
		 FileHandler fh;

	        try {
	        	
	            fh = new FileHandler("C:\\Users\\diego.alvaradocastil\\hotelBedsLog\\hotelbedshackerDetector.log", true);
	            log.addHandler(fh);

	            SimpleFormatter formatter = new SimpleFormatter();
	            fh.setFormatter(formatter);

	             log.info(line);
	             response = "OK";
	             fh.close();

	        } catch (SecurityException e) {
	        	response = "KO";
	            e.printStackTrace();
	        } catch (IOException e) {
	        	response = "KO";
	            e.printStackTrace();
	        }
		return response;
	}

}
