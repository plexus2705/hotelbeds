package com.hotelbeds.supplierintegrations.hackertest.detector.impl;



import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;
import com.hotelbeds.supplierintegrations.hackertest.entity.Fallo;

@Service
public class HackerDetectorImpl implements HackerDetector{


	
	public static final String SIGNIN_FAILURE = "SIGNIN_FAILURE";
	public static final long MINUTOS = 5;
	
	private List<Fallo> fallos = new ArrayList<>();
	
	@Override
	public String parseLine(String line) {
		String response = null;
		
		String[] args = line.split(",");
		long minutos= 0;
		
		Fallo f = null;
		String ip = args[0];
		String date = args[1];
		String action = args[2];
		String username = args[3];
		long idFallo = 2;
		
		Date currentDate = new Date();
		long currentEpoch = currentDate.getTime() / 1000;
		
		
		Integer contadorIp = 0;
		List<Long> idsEliminar = new ArrayList<>();
		
		if((SIGNIN_FAILURE).equals(action)) {
			if(fallos.size()>0) {
				
				for( int i= 0 ; i< fallos.size();i++) {
					minutos = timeCalculation(date,Long.toString(currentEpoch));

					if(minutos <= MINUTOS) {
						contadorIp += contadorIps(ip, fallos.get(i).getIp());
						
					}else {
						
						idsEliminar.add(fallos.get(i).getId());
					}
					
				}
			}
			if(contadorIp >= 5) {
				response = "IP sospechosa";
			}
			for(Long id : idsEliminar) {
				for( int i= 0 ; i< fallos.size();i++) {
					if(fallos.get(i).getId() == id) {
						fallos.remove(fallos.get(i));
					}
				}
			}
		}
		f = new Fallo();
		f.setId(idFallo++);
		f.setIp(ip);
		f.setUser(username);
		f.setDate(currentDate);
		fallos.add(f);

		

		return response;
	}
	
	private long timeCalculation(String date, String currentDate)  {

	        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        format.setTimeZone(TimeZone.getTimeZone("UTC"));
	        long epoch = Long.parseLong( date );
	        Timestamp oldTimestamp = new Timestamp(new Date( epoch * 1000 ).getTime());
	        epoch = Long.parseLong( currentDate );
	        Timestamp currentTimestamp = new Timestamp(new Date( epoch * 1000 ).getTime());
	        
	        long diff = currentTimestamp.getTime() - oldTimestamp.getTime(); 
	        long diffMinutes = diff/(60 * 1000);
	        
	        
	        return (long) Math.floor(diffMinutes);
	}
	
	
	private Integer contadorIps(String ip, String ipAlmacenada) {
		Integer contador = 0;
		if(ipAlmacenada.equals(ip)) {
			contador = 1;
		}
		return contador;
	}
	
	
	
}
