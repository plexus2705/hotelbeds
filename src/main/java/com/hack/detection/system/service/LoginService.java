package com.hack.detection.system.service;

import javax.servlet.http.HttpServletRequest;

import com.hack.detection.system.entity.Usuario;

public interface LoginService {

	public Usuario login(String ip ,String user, String pass);
}
