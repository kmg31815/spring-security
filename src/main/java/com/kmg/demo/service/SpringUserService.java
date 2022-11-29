package com.kmg.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kmg.demo.entity.AppUser;

@Service
public class SpringUserService implements UserDetailsService {

	@Autowired
	private AppUserService appUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username: " + username);
		AppUser appUser = appUserService.getByUsername(username);

		// 第三個參數是 authorities，是用來定義使用者擁有的權限。 目前先給予空 List
		return new User(appUser.getUsername(), appUser.getPassword(), Collections.emptyList());
	}

}
