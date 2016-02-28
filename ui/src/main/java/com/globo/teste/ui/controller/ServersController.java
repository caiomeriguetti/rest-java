package com.globo.teste.ui.controller;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.globo.teste.ui.config.Env;
 
@Controller
public class ServersController {
	
	@RequestMapping(
		value="/servers/{id}/uninstall/{package}", 
		produces="application/json", 
		method=RequestMethod.DELETE
	)
	@ResponseBody
	public ResponseEntity<String> delPackages(@PathVariable("id") String id,
			@PathVariable("package") String packageName) {
	    RestTemplate request = new RestTemplate();
	    try {
	    	return request.exchange(
	    		backendUrl("/servers/"+id+"/uninstall/"+packageName), HttpMethod.DELETE, 
        		null, String.class
    	    );
	    } catch (HttpServerErrorException e) {
	    	return new ResponseEntity<String>(null, null, e.getStatusCode());
	    }

	}
	
	@RequestMapping(
		value="/servers/{id}/install/{package}", 
		produces="application/json", 
		method=RequestMethod.PUT
	)
	@ResponseBody
	public ResponseEntity<String> installPackages(@PathVariable("id") String id,
			@PathVariable("package") String packageName) {
	    RestTemplate request = new RestTemplate();
	    try {
	    	return request.exchange(
	    		backendUrl("/servers/"+id+"/install/"+packageName), HttpMethod.PUT, 
        		null, String.class
    	    );
	    } catch (HttpServerErrorException e) {
	    	return new ResponseEntity<String>(null, null, e.getStatusCode());
	    }
	}
	
	@RequestMapping(value="/servers/{id}/packages", produces="application/json")
	@ResponseBody
	public ResponseEntity<String> listPackages(@PathVariable("id") String id) {
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    RestTemplate request = new RestTemplate();
	    String response = request.getForObject(backendUrl("/servers/"+id+"/packages"), String.class);
	    
	    return new ResponseEntity<String>(response, httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/servers", produces="application/json")
	@ResponseBody
	public ResponseEntity<String> listServers() {
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    RestTemplate request = new RestTemplate();
	    String response = request.getForObject(backendUrl("/servers"), String.class);
	    
	    return new ResponseEntity<String>(response, httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(
		value="/servers", 
		produces="application/json", 
		method=RequestMethod.POST
	)
	@ResponseBody
	public ResponseEntity<String> saveServer(
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam("ip") String ip,
			@RequestParam("user") String user,
			@RequestParam("password") String password,
			@RequestParam(value="distribution", defaultValue="") String distribution) {
	    
	    MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
	    data.add("name", name);
	    data.add("ip", ip);
	    data.add("user", user);
	    data.add("password", password);
		data.add("distribution", distribution);
	    
	    RestTemplate request = new RestTemplate();
        ResponseEntity<String> response = request.postForEntity(backendUrl("/servers"), data, String.class);
	    
	    return response;
	}
	
	@RequestMapping(
		value="/servers/{id}", 
		method=RequestMethod.DELETE
	)
	@ResponseBody
	public ResponseEntity<String> deleteServer(
			@PathVariable("id") String id) {
	    
	    RestTemplate request = new RestTemplate();
	    
	    try {
	    	return request.exchange(
	    		backendUrl("/servers/"+id), HttpMethod.DELETE, 
        		null, String.class
    	    );
	    } catch (Exception e) {
	    	return new ResponseEntity<String>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(
		value="/servers/{id}", 
		produces="application/json", 
		method=RequestMethod.POST
	)
	@ResponseBody
	public ResponseEntity<String> updateServer(
			@PathVariable("id") String id,	
			@RequestBody MultiValueMap<String,String> body) {
		
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
	    MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
	    data.add("name", body.getFirst("name"));
	    data.add("ip", body.getFirst("ip"));
	    data.add("user", body.getFirst("user"));
	    data.add("password", body.getFirst("password"));
		data.add("distribution", body.getFirst("distribution"));
		
	    RestTemplate request = new RestTemplate();
	    ResponseEntity<String> response = request.exchange(
    		backendUrl("/servers/"+id), HttpMethod.PUT, 
    		new HttpEntity<>(data, null), String.class
	    );
	    
	    return response;
	}
	
	private String backendUrl(String path) {
		return Env.getBackendUrl() + path;
	}
}