package com.globo.teste.rest;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.globo.teste.model.GenericMessage;
import com.globo.teste.model.Server;
import com.globo.teste.model.ServerPackage;
import com.globo.teste.services.ServerService;

@Path("servers")
public class ServersResource {

	@Inject ServerService serverService;
	
	@DELETE
	@Path("{id}/{package}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removePackage(@PathParam("id") String id,
			   					  @PathParam("package") String packageName) {
		
		String installResult = serverService.uninstallPackage(id, packageName);
	    
	    GenericMessage result = new GenericMessage();
	    result.text = installResult;
	    result.code = (!serverService.hasPackage(id, packageName)) ? 1 : 0;
	    
		return Response.status(200).entity(result).build();
		
	}
	
	@PUT
	@Path("{id}/{package}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response installPackage(@PathParam("id") String id,
								   @PathParam("package") String packageName) {
	    
	    String installResult = serverService.installPackage(id, packageName);
	    
	    GenericMessage result = new GenericMessage();
	    result.text = installResult;
	    result.code = (serverService.hasPackage(id, packageName)) ? 1 : 0;
	    
		return Response.status(200).entity(result).build();
	}
	
	@GET
	@Path("{id}/packages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listPackages(@PathParam("id") String id) {
	    
	    ServerPackage[] packages = serverService.getPackages(id);
	    Object entity = packages;
	    if (entity == null) {
	    	GenericMessage response = new GenericMessage();
	    	response.code = 0;
	    	response.text = "Couldnt retrieve server packages";
	    	
	    	entity = response;
	    }
	    
	    Response r = Response.status(200).entity(entity).build();
	      
		return r;
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {
		
		boolean success = serverService.delete(id);
		
		GenericMessage response = new GenericMessage();
		response.code=(success) ? 1: 0;
		
		return Response.status(200).entity(response).build();
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,
						   @FormParam("user") String user,
						   @FormParam("name") String name,
						   @FormParam("password") String password,
				           @FormParam("ip") String ip,
				           @FormParam("distribution") String distribution) {
		
		Server toBeUpdated = new Server();
		toBeUpdated.setIp(ip);
		toBeUpdated.setUser(user);
		toBeUpdated.setPassword(password);
		toBeUpdated.setDistribution(distribution);
		toBeUpdated.setName(name);
		
		boolean updated = serverService.update(id, toBeUpdated);
		GenericMessage response = new GenericMessage();
		if (!updated) {
			response.code = 0;
		} else {
			response.code = 1;
		}
		
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServerById(@PathParam("id") String id) {
	    Server server = serverService.getServerById(id);
	    Object response = server;
	    if (server == null) {
	    	response = new GenericMessage();
	    	((GenericMessage)response).code = 0;
	    	((GenericMessage)response).text = "Object doesnt exist";
	    }
		return Response.status(200).entity(response).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
	    Server[] servers = serverService.list();
		return Response.status(200).entity(servers).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@FormParam("user") String user,
						@FormParam("name") String name,
						@FormParam("password") String password,
	                    @FormParam("ip") String ip,
	                    @FormParam("distribution") String distribution) {
	    
	    Server newServer = new Server();
	    newServer.setIp(ip);
	    newServer.setUser(user);
	    newServer.setPassword(password);
	    newServer.setDistribution(distribution);
	    newServer.setName(name);
	    
	    serverService.save(newServer);
	    
	    return Response.status(200).entity(newServer).build();
	}


}
