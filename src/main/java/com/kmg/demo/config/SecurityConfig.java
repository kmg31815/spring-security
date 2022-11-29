package com.kmg.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 讓此類別的安全性設置生效 (包含csrf保護也會自動生效)
 * 
 * @EnableWebSecurity 本身配置了 @Configuration
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Spring 會自動找到有實作這個介面的類別，也就是 SpringUserService
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/*
	 * 設定 API 授權規則
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests() // 開始自訂授權規則
				.antMatchers(HttpMethod.GET, "/users/**").authenticated() // 驗證規則(?
				.antMatchers(HttpMethod.GET).permitAll() // 不驗證
				.antMatchers(HttpMethod.POST, "/users").permitAll() // 不驗證

				// 對剩下的 API 定義規則
				.anyRequest().authenticated() // 驗證規則(?

				.and()

				// 關閉對 CSRF（跨站請求偽造）攻擊的防護。這樣 Security 機制才不會拒絕外部直接對 API 發出的請求，如 Postman 與前端
				.csrf().disable()

				.formLogin(); // 啟用內建的登入畫面
//				.httpBasic(); // dialog 登入
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				// passwordEncoder()傳入密碼加密器 => 登入時輸入的密碼會被加密，與資料庫中使用者的已加密密碼進行比對
				.passwordEncoder(new BCryptPasswordEncoder());
	}

//	=================================================================================================
	
	
	
//	=================================================================================================

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests() // 開始自訂授權規則
//
//		// 傳入 HttpMethod 與 API 路徑，後面接著授權方式，這樣就定義好一個規則
////		.antMatchers(HttpMethod.GET, "/security/*").authenticated() // 驗證規則(?
//				.antMatchers(HttpMethod.GET, "/security/test1/**").permitAll() // 不驗證
//				.antMatchers(HttpMethod.GET, "/security/test4/*").permitAll() // 不驗證
//				.antMatchers(HttpMethod.POST, "/security/test5").permitAll() // 不驗證
//
//				// 對剩下的 API 定義規則
//				.anyRequest().authenticated() // 驗證規則(?
//
//				.and()
//
//				// 關閉對 CSRF（跨站請求偽造）攻擊的防護。這樣 Security 機制才不會拒絕外部直接對 API 發出的請求，如 Postman 與前端
//				.csrf().disable()
//
//				.formLogin(); // 啟用內建的登入畫面
////		.httpBasic(); // dialog 登入
//	}

}
