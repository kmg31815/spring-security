package com.kmg.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmg.demo.dao.AppUserRepository;
import com.kmg.demo.entity.AppUser;

@Service
public class AppUserService {

	@Autowired
	private AppUserRepository dao;

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public AppUser createUser(AppUser appUser) {
		// 存入 DB 前對 password 作加密
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		return dao.save(appUser);
	}

	public AppUser getByUsername(String username) {
		Optional<AppUser> appUser = dao.findByUsername(username);
		if (appUser.isEmpty()) {
			return null;
		}
		return appUser.get();
	}

	public AppUser getById(int id) {
		Optional<AppUser> appUser = dao.findById(id);
		if (appUser.isEmpty()) {
			return null;
		}
		return appUser.get();
	}

	public List<AppUser> getAllUsers() {
		return dao.findAll();
	}

}
