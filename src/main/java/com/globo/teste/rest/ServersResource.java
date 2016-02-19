package com.globo.teste.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.globo.teste.db.DB;
import com.globo.teste.model.Server;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@Path("servers")
public class ServersResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
	    
	    MongoCollection<Document> serversCollection = serversCollection();
	    
	    FindIterable<Document> servers = serversCollection.find();
		return Response.status(200).entity(Server.fromIterable(servers)).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@FormParam("name") String name,
	                    @FormParam("ip") String ip) {
	    
	    Server newServer = new Server();
	    newServer.setIp(ip);
	    newServer.setName(name);
	    
	    MongoCollection<Document> serversCollection = serversCollection();
	    Document doc = newServer.toDocument();
	    serversCollection.insertOne(doc);
	    
	    newServer.setId(doc.getObjectId("_id").toHexString());
	    
	    return Response.status(200).entity(newServer).build();
	}
	
	private MongoCollection<Document> serversCollection() {
	    return DB.get().getCollection("servers");
	}

}
