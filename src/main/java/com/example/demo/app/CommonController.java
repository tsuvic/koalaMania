package com.example.demo.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {

	@GetMapping("/privacypolicy")
	public String privacypolicy() {
		return "privacypolicy";
	}

	@GetMapping("/terms")
	public String terms() {
		return "terms";
	}

}