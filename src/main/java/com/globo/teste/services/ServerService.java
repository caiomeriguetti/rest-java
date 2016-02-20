package com.globo.teste.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.globo.teste.db.DB;
import com.globo.teste.model.Server;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class ServerService {
	
	public boolean installPackage(String id, String packageName) {
		return false;
	}
	
	public String[] getPackages (String id) {
		Server server = getServerById(id);
	    
	    JSch jsch=new JSch();
	    try {
	    	Session session = jsch.getSession(server.getUser(), server.getIp(), 22);
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	session.setConfig(config);
	    	
	    	session.setPassword(server.getPassword());
		    
		    session.connect(30000);

		    Channel channel=session.openChannel("exec");
		    
		    ((ChannelExec)channel).setCommand("dpkg -l");

			  InputStream in=channel.getInputStream();
			  OutputStream out=channel.getOutputStream();
			  ((ChannelExec)channel).setErrStream(System.err);
			
			  channel.connect();
			
			  out.flush();
			
			  byte[] tmp=new byte[1024];
			  StringBuilder builder = new StringBuilder();
			  while(true){
			    while(in.available()>0){
			      int i=in.read(tmp, 0, 1024);
			      if(i<0)break;
			      builder.append(new String(tmp, 0, i));
			    }
			    if(channel.isClosed()){
			      break;
			    }
			    try{Thread.sleep(1000);}catch(Exception ee){}
			  }
			  
			  System.out.println(builder.toString());
			  channel.disconnect();
			  session.disconnect();
		      
	    } catch (JSchException | IOException e) {
	    	System.out.println(e.getMessage());
	    	return null;
	    }
	    
	    
	    
	    return null;
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
