package com.globo.teste.ui.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class ServersController {
	@RequestMapping("/servers")
	public String listServers() {
		return null;
	}
}