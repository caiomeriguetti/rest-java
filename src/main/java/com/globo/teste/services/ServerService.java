package com.globo.teste.services;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.globo.teste.db.DB;
import com.globo.teste.model.Server;
import com.globo.teste.model.ServerPackage;
import com.globo.teste.ssh.RemoteCommand;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class ServerService {
	
	public boolean installPackage(String id, String packageName) {
		return false;
	}
	
	public ServerPackage[] getPackages (String id) {
		Server server = getServerById(id);
	    
	    RemoteCommand cmd = new RemoteCommand("dpkg --get-selections");
	    String result = cmd.execute(server);
	    
	    String[] lines = result.split("\n");
	    ServerPackage[] packages = new ServerPackage[lines.length];
	    for (int i = 0; i < lines.length; i++) {
	    	String line = lines[i];
	    	String packageName = line.split("\\s+")[0];
	    	
	    	ServerPackage pack = new ServerPackage();
	    	pack.setName(packageName);
	    	packages[i] = pack;
	    }
	    
	    return packages;
	}
	
	public Server[] list() {
		MongoCollection<Document> serversCollection = serversCollection();
	    
	    FindIterable<Document> servers = serversCollection.find();
	    
	    return Server.fromIterable(servers);
	}
	
	public Server getServerById(String id) {
		MongoCollection<Document> serversCollection = serversCollection();
		
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    
	    Document serverDoc = serversCollection.find(query).first();
	    return Server.fromDocument(serverDoc);
	}
	
	public Server save(Server newServer) {
		MongoCollection<Document> serversCollection = serversCollection();
	    Document doc = newServer.toDocument();
	    serversCollection.insertOne(doc);
	    
	    newServer.setId(doc.getObjectId("_id").toHexString());
	    
	    return newServer;
	}
	
	private MongoCollection<Document> serversCollection() {
	    return DB.get().getCollection("servers");
	}
	
}
