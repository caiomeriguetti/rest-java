package com.globo.teste.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.globo.teste.model.Server;
import com.globo.teste.services.ServerService;

@Path("servers")
public class ServersResource {
	
	@Inject ServerService serverService;
	
	@DELETE
	@Path("{id}/uninstall/{package}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removePackage(@PathParam("id") String id,
			   					  @PathParam("package") String packageName) {
		
		
		return null;
		
	}
	
	@POST
	@Path("{id}/install/{package}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response installPackage(@PathParam("id") String id,
								   @PathParam("package") String packageName) {
	    
	    final boolean installed = serverService.installPackage(id, packageName);
	    
	    Object result = new Object(){
	    	public boolean success = installed;
	    };
	    
		return Response.status(200).entity(result).build();
	}
	
	@GET
	@Path("{id}/packages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listPackages(@PathParam("id") String id) {
	    
	    String[] packages = serverService.getPackages(id);
	    
		return Response.status(200).entity(packages).build();
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
	    Server[] servers = serverService.list();
		return Response.status(200).entity(servers).build();
	}
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@FormParam("user") String user,
						@FormParam("password") String password,
	                    @FormParam("ip") String ip,
	                    @FormParam("distribution") String distribution) {
	    
	    Server newServer = new Server();
	    newServer.setIp(ip);
	    newServer.setUser(user);
	    newServer.setPassword(password);
	    newServer.setDistribution(distribution);
	    
	    serverService.save(newServer);
	    
	    return Response.status(200).entity(newServer).build();
	}


}
