package com.hack.detection.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hack.detection.system.entity.Fallo;
import com.hack.detection.system.entity.Usuario;
import com.hack.detection.system.security.HackerDetector;
import com.hack.detection.system.service.LoginService;

@Service
public class LoginServiceImpl  implements LoginService{

	
	
	public static final String SIGNIN_SUCCESS = "SIGNIN_SUCCESS";
	public static final String SIGNIN_FAILURE = "SIGNIN_FAILURE";
	
	@Autowired 
	private HackerDetector hackerDetector;
	
	private List<Fallo> fallos = new ArrayList<>();
	Fallo f = null;

	long idFallo = 0;
	
	
	@Override
	public Usuario login(String ip,String user, String pass) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(ip+",");
		Date currentDate = new Date();
        long epoch = currentDate.getTime() / 1000;
        sBuilder.append(epoch+",");
		List<Usuario> usuarios = usuarios();
		Usuario response = null;
		List<Long> idsEliminar = new ArrayList<>();
		
		boolean loginSuccess= false;
		for(Usuario u : usuarios) {
			if(u.getNombre().equals(user) && u.getPass().equals(pass) ) {
				response = u;
				loginSuccess= true;			
			}
			
		}
		if(loginSuccess) {
			sBuilder.append(SIGNIN_SUCCESS+","+user);	
		}else {
			sBuilder.append(SIGNIN_FAILURE+","+user);
			Date tiempoActual = new Date();
			Integer contadorIp = 0;
			if(fallos.size()>0) {
				
				for( int i= 0 ; i< fallos.size();i++) {
					long diferencia= tiempoActual.getTime()- fallos.get(i).getDate().getTime();
					long minutos = TimeUnit.MILLISECONDS.toMinutes(diferencia); 
					if(minutos <= 5) {
						contadorIp += contadorIps(ip, fallos.get(i).getIp());
						
					}else {
						
						idsEliminar.add(fallos.get(i).getId());
					}
					
				}
			}
			if(contadorIp >= 5) {
				StringBuilder sBuilder1 = new StringBuilder();
				sBuilder1.append("IP sospechosa: "+ip);
				hackerDetector.parseLine(sBuilder1 .toString());
			}
			for(Long id : idsEliminar) {
				for( int i= 0 ; i< fallos.size();i++) {
					if(fallos.get(i).getId() == id) {
						fallos.remove(fallos.get(i));
					}
				}
			}
			f = new Fallo();
			f.setId(idFallo++);
			f.setIp(ip);
			f.setUser(user);
			f.setDate(tiempoActual);
			fallos.add(f);
		}
		
		hackerDetector.parseLine(sBuilder.toString());
		return response;
	}
	
	private Integer contadorIps(String ip, String ipAlmacenada) {
		Integer contador = 0;
		if(ipAlmacenada.equals(ip)) {
			contador = 1;
		}
		return contador;
	}
	
	private List<Usuario> usuarios(){
		
		List<Usuario> usuarios = new ArrayList<>();
		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNombre("admin");
		u1.setPass("1234");
		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNombre("user");
		u2.setPass("abc");
		usuarios.add(u1);
		usuarios.add(u2);
		
		return usuarios;
	}

}
