package com.kmg.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kmg.demo.entity.AppUser;
import com.kmg.demo.service.AppUserService;

@RestController
public class SecurityController {

	@Autowired
	private AppUserService service;

	@PostMapping("/users")
	public AppUser createUser(@RequestBody AppUser appUser) {
		return service.createUser(appUser);
	}

	@GetMapping("/users")
	public List<AppUser> getAllUsers() {
		return service.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public AppUser getUser(@PathVariable int id) {
		return service.getById(id);
	}

	@GetMapping("/hello")
	public String getHello() {
		return "hello~~";
	}

}
