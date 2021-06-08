package com.panimalar.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {
	
	@GetMapping("/get")
	public String getDemo() {
		return "Hello World";
	}

}
