package com.kmg.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/security")
@RestController
public class SecurityControllerTest {

	@GetMapping("/test1/1")
	public String testString() { // 不驗證
		return "test";
	}

	@GetMapping("/test1/1/2")
	public String testString2() { // 不驗證
		return "test2";
	}

	@GetMapping("/test3")
	public String testString3() {
		return "test3";
	}

	@GetMapping("/test4/{id}")
	public String testString4(@PathVariable int id) { // 不驗證
		return "test4 " + id;
	}

	@PostMapping("/test5")
	public String testString5(@RequestBody int id) { // 不驗證
		return "test5 " + id;
	}

}
