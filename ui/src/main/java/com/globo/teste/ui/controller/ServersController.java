package com.globo.teste.ui.controller;
 
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.globo.teste.ui.config.Env;
 
@Controller
public class ServersController {
	@RequestMapping(value="/servers", produces="application/json")
	@ResponseBody
	public ResponseEntity<String> listServers() {
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    RestTemplate request = new RestTemplate();
	    String response = request.getForObject(backendUrl("/servers"), String.class);
	    
	    return new ResponseEntity<String>(response, httpHeaders, HttpStatus.OK);
	}
	
	private String backendUrl(String path) {
		return Env.getBackendUrl() + path;
	}
}