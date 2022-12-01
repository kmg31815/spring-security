package com.kmg.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.kmg.demo.util.UserAccess;
import com.kmg.demo.util.UserLogoutHandler;

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

	@Autowired
	private UserLogoutHandler logoutHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests() // 開始自訂授權規則
				.antMatchers(HttpMethod.POST, "/users").permitAll() // 允許所有請求
				.antMatchers(HttpMethod.GET, "/users").hasAuthority(UserAccess.ADMIN.toString()) // 需具備管理員權限才可存取
				.antMatchers(HttpMethod.GET, "/users/*").authenticated() // 只要通過身份驗證即可存取
				.antMatchers(HttpMethod.GET).permitAll() // 允許所有請求
				.anyRequest().authenticated() // (對剩下的 API 定義規則) 通過身份驗證即可存取
				
				.and().formLogin() // 啟用內建的登入畫面
//				.httpBasic() // dialog 登入
				
				.and().logout().logoutUrl("/logout").addLogoutHandler(logoutHandler)
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
//				.logoutSuccessUrl("/hello")
				
				.and()
				// 關閉對 CSRF（跨站請求偽造）攻擊的防護。這樣 Security 機制才不會拒絕外部直接對 API 發出的請求，如 Postman 與前端
				.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				// passwordEncoder()傳入密碼加密器 => 登入時輸入的密碼會被加密，與資料庫中使用者的已加密密碼進行比對
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	/*
	 * BasicAuthenticationFilter.doFilterInternal 會
	 * 
	 * 1. 將 "用戶輸入的帳號密碼" 轉換成 UsernamePasswordAuthenticationToken
	 * 
	 * 2. 將上述產生的 token 傳遞給 AuthenticationManager 進行 "登錄認證"
	 * 
	 * 3. 認證成功後會回傳 封裝了"用戶權限"等資料的 Authentication (不包含token)
	 * 		- 這裡"封裝了用戶權限等資料的 Authentication" 就是 SpringUserService 回傳的 UserDetails
	 * 
	 * 4. 最後將該 Authentication 實例賦予給當前的 SecurityContext(即該次請求的"身份狀態")
	 */

}
