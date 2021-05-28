package com.hack.detection.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hack.detection.system.entity.Usuario;
import com.hack.detection.system.service.LoginService;

@RestController
public class HotelBedsController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	private String login(HttpServletRequest request, @RequestBody Usuario user) {
		String respuesta = "";
		try {
			
			String ip = getRemortIP(request);
		
			Usuario usuario = loginService.login(ip,user.getNombre(), user.getPass());
			
			if(usuario == null) {
				respuesta = "El usuario o el password no es correcto";
			}else {
				respuesta = "Hola "+ usuario.getNombre();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respuesta;

	}
	
	private String getRemortIP(HttpServletRequest request) {   
		   if (request.getHeader("x-forwarded-for") == null) {   
		      return request.getRemoteAddr();   
		   }   
		   return request.getHeader("x-forwarded-for");   
		}
	
}
