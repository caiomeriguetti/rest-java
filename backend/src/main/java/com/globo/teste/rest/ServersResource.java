package com.globo.teste.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
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
import javax.ws.rs.core.Response.Status;

import com.globo.teste.model.GenericMessage;
import com.globo.teste.model.Server;
import com.globo.teste.model.ServerPackage;
import com.globo.teste.services.ServerService;

@Path("servers")
public class ServersResource {

	@Inject ServerService serverService;
	
	@DELETE
	@Path("{id}/uninstall/{package}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removePackage(@PathParam("id") String id,
								  @NotNull @PathParam("package") String packageName) {
		
		if (packageName.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		boolean success = serverService.uninstallPackage(id, packageName);
	    
		if (!success) {
			return Response.serverError().build();
		}
		
		return Response.ok().build();
		
	}
	
	@PUT
	@Path("{id}/install/{package}")
	public Response installPackage(@PathParam("id") String id,
								   @NotNull @PathParam("package") String packageName) {
		
		List<String> installed = new ArrayList<String>();
		List<String> notInstalled = new ArrayList<String>();
		
		String[] names;
		
		if (packageName.contains(" ")) {
			names = packageName.split(" ");
			
		} else {
			names = new String[1];
			names[0] = packageName;
		}

		for (String pName: names) {
			if (installed.contains(pName)) {
				continue;
			}
			
			String success = serverService.installPackage(id, pName);
			
			if (success != null) {
				installed.add(pName);
			} else {
				notInstalled.add(pName);
			}
		}
	    
	    if (installed.size() == 0) {
	    	return Response.serverError().build();
	    }
	    
	    InstallPackageResponse entity = new InstallPackageResponse();
	    entity.installed = installed.toArray(new String[installed.size()]);
	    entity.notInstalled = notInstalled.toArray(new String[notInstalled.size()]);
	    
		return Response.ok().entity(entity).build();
	}
	
	@GET
	@Path("{id}/packages")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listPackages(@NotNull @PathParam("id") String id) {
	    
	    ServerPackage[] packages = serverService.getPackages(id);
	    if (packages == null) {
	    	GenericMessage response = new GenericMessage();
	    	response.text = "Couldnt retrieve server packages";
	    	return Response.serverError().entity(response).build();
	    }
	    
		return Response.ok().entity(packages).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@NotNull @PathParam("id") String id) {
		
		GenericMessage entity = new GenericMessage();
		
		try {
			serverService.delete(id);
			entity.text = "Server deleted successfully";
		} catch (Exception e) {
			entity.text = "Error deleting the server "+id;
			return Response.serverError().build();
		}
		
		return Response.status(Response.Status.OK).entity(entity).build();
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,
						   @NotNull @FormParam("user") String user,
						   @FormParam("name") String name,
						   @NotNull @FormParam("password") String password,
						   @NotNull @FormParam("ip") String ip,
				           @FormParam("distribution") String distribution) {
		
		Server toBeUpdated = new Server();
		toBeUpdated.setIp(ip);
		toBeUpdated.setUser(user);
		toBeUpdated.setPassword(password);
		toBeUpdated.setDistribution(distribution);
		toBeUpdated.setName(name);
		
		boolean updated = serverService.update(id, toBeUpdated);
		GenericMessage response = new GenericMessage();;
		if (!updated) {
			response.text = "Couldnt update the server info";
			return Response.serverError().entity(response).build();
		} else {
			response.text = "Server "+id+" was updated successfully";
		}
		
		return Response.ok().entity(response).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServerById(@NotNull @PathParam("id") String id) {
		Server server = null;
		try {
			server = serverService.getServerById(id);
		} catch (Exception e) {
			GenericMessage message = new GenericMessage();
	    	message.text = "Problem retrieving server info";
			return Response.serverError().entity(server).build();
		}
		
	    if (server == null) {
	    	GenericMessage message = new GenericMessage();
	    	message.text = "Server doesnt exist";
	    	
	    	return Response.status(Response.Status.BAD_REQUEST)
	    				   .entity(message).build();
	    }
	    
		return Response.ok().entity(server).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		Server[] servers = null;
		try {
			servers = serverService.list();
		} catch (Exception e) {
			GenericMessage message = new GenericMessage();
			message.text = "Couldnt retrieve servers list";
			return Response.serverError().entity(message).build();
		}
		
		return Response.ok().entity(servers).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@NotNull @FormParam("user") String user,
						@FormParam("name") String name,
						@NotNull @FormParam("password") String password,
						@NotNull @FormParam("ip") String ip,
	                    @FormParam("distribution") String distribution) {
	    
	    Server newServer = new Server();
	    newServer.setIp(ip);
	    newServer.setUser(user);
	    newServer.setPassword(password);
	    newServer.setDistribution(distribution);
	    newServer.setName(name);
	    
	    GenericMessage message = new GenericMessage();
	    
	    try {
	    	serverService.save(newServer);
	    	message.text = "Server added successfully";
	    	message.data = newServer.getId();
	    	
	    	return Response.ok().entity(message).build();
	    } catch (Exception e) {
			message.text = "Problem adding the server";
			return Response.serverError().entity(message).build();
	    }
	    
	}
	
	/**
	 * Responses
	 * */
	
	public static class InstallPackageResponse {
		public String[] installed;
		public String[] notInstalled;
	}


}
