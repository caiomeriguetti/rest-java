package com.globo.teste.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.globo.teste.db.DB;
import com.globo.teste.model.Server;
import com.globo.teste.model.ServerPackage;
import com.globo.teste.ssh.CommandBuilder;
import com.globo.teste.ssh.CommandBuilderFactory;
import com.globo.teste.ssh.RemoteCommand;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class ServerServiceImpl implements ServerService {
	
	/**
	 * Uninstall a package on a server
	 * */
	public String uninstallPackage(String id, String packageName) {
		Server server = getServerById(id);
		CommandBuilder builder = CommandBuilderFactory.getBuilder(server);
		
		RemoteCommand cmd = new RemoteCommand(builder.uninstallPackageCommand(packageName));
		cmd.setSudo(true);
	    String result = cmd.execute(server);
	    if (!this.hasPackage(server, packageName)) {
	    	return result;
	    }
		
		return null;
	}
	
	/**
	 * Check if a package is installed on a server
	 * */
	public boolean hasPackage(Server server, String packageName) {
		CommandBuilder builder = CommandBuilderFactory.getBuilder(server);
		RemoteCommand cmd = new RemoteCommand(builder.isPackageInstalledCommand(packageName));
	    String result = cmd.execute(server);

		return result.indexOf("Status: install ok installed") >= 0;
	}
	public boolean hasPackage(String id, String packageName) {
		Server server = this.getServerById(id);
		return this.hasPackage(server, packageName);
	}
	
	/**
	 * Install a package on a server
	 * */
	public String installPackage(String id, String packageName) {
		Server server = getServerById(id);
		CommandBuilder builder = CommandBuilderFactory.getBuilder(server);
		RemoteCommand cmd = new RemoteCommand(builder.installPackageCommand(packageName));
		cmd.setSudo(true);
	    String result = cmd.execute(server);
		
	    if (this.hasPackage(server, packageName)) {
	    	return result;
	    }
	    
		return null;
	}
	
	/**
	 * Get packages installed on a server
	 * */
	public ServerPackage[] getPackages (String id) {
		Server server = getServerById(id);
		CommandBuilder builder = CommandBuilderFactory.getBuilder(server);
	    RemoteCommand cmd = new RemoteCommand(builder.listPackagesCommand());
	    String result = cmd.execute(server);
	    
	    List<ServerPackage> packages = null;
	    
	    if (result != null) {
		    String[] lines = result.split("\n");
		    packages = new ArrayList<ServerPackage>();
		    for (int i = 0; i < lines.length; i++) {
		    	String line = lines[i];
		    	String[] parts = line.split("\\s+");
		    	
		    	if (parts[1].toLowerCase().equals("install")) {
			    	String packageName = parts[0];
			    	
			    	ServerPackage pack = new ServerPackage();
			    	pack.setName(packageName);
			    	packages.add(pack);
		    	}
		    }
	    }
	    
	    return packages.toArray(new ServerPackage[packages.size()]);
	}
	
	/**
	 * list of all servers
	 * */
	public Server[] list() {
		MongoCollection<Document> serversCollection = serversCollection();
	    
	    FindIterable<Document> servers = serversCollection.find().sort(new Document("_id", -1));
	    
	    return Server.fromIterable(servers);
	}
	
	public Server getServerById(String id) {
		MongoCollection<Document> serversCollection = serversCollection();
		
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    
	    Document serverDoc = serversCollection.find(query).first();
	    return (serverDoc != null) ? Server.fromDocument(serverDoc) : null;
	}
	
	/**
	 * Delete a server
	 * */
	public boolean delete(String id) {
		
		MongoCollection<Document> serversCollection = serversCollection();
		
		Document filter = new Document();
		filter.put("_id", new ObjectId(id));
		
		DeleteResult result = serversCollection.deleteOne(filter);
		
		return result.wasAcknowledged();
	}
	
	/**
	 * Update only not empty fields
	 * */
	public boolean update(String id, Server server) {
		return this.update(id, server, true);
	}
	
	/**
	 * Update server fields
	 * 
	 * @param onlyNotEmptyFields If true, update only the not empty fields. If false, update all fields
	 * */
	public boolean update(String id, Server updatedServerData, boolean onlyNotEmptyFields) {
		MongoCollection<Document> serversCollection = serversCollection();
		Document updatedServerDocument = updatedServerData.toDocument();
		Document updateDocument;
		
		if (onlyNotEmptyFields) {
			updateDocument = new Document();
			Document fields = new Document();
			for(String key: updatedServerDocument.keySet()){
				Object val = updatedServerDocument.get(key);
				
				if (val != null) {
					fields.put(key, val);
				}
			}
			updateDocument.put("$set", fields);
		} else {
			updateDocument = updatedServerDocument;
		}
		

		Document searchQuery = new Document();
		searchQuery.put("_id", new ObjectId(id));
		
		UpdateResult result = serversCollection.updateMany(searchQuery, updateDocument);
		
		return result.getMatchedCount() >= 1;
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
