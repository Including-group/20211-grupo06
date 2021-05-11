package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@GetMapping("/")
	public ModelAndView menu() {
		return new ModelAndView("paginaMenu");
	}

	@GetMapping("/login")
	public ModelAndView autenticacao() {
		return new ModelAndView("paginaLogin");
	}

	@GetMapping("/error")
	public ModelAndView error() {
		return new ModelAndView("error");
	}
}
