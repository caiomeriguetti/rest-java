package com.globo.teste.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Server {
    public String id;
    public String ip;
    public String user;
    public String password;
	public String distribution;
	public String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    
    public Document toDocument () {
        Document doc = new Document();
        doc.put("user", this.getUser());
        doc.put("ip", this.getIp());
        doc.put("distribution", this.getDistribution());
        doc.put("password", this.getPassword());
        doc.put("name", this.getName());
        return doc;
    }
    
    public static Server fromDocument (Document doc) {
        Server s = new Server();
        s.setIp(doc.getString("ip"));
        s.setUser(doc.getString("user"));
        s.setPassword(doc.getString("password"));
        s.setDistribution(doc.getString("distribution"));
        s.setName(doc.getString("name"));
        ObjectId id = doc.getObjectId("_id");
        if (id != null) {
        	s.setId(id.toHexString());
        }
        return s;
    }
    
    public static Server[] fromIterable (FindIterable<Document> iterable) {
        List<Server> list = new ArrayList<Server>();
        for (Document doc: iterable) {
            list.add(Server.fromDocument(doc));
        }
        return list.toArray(new Server[list.size()]);
    }
    
}
