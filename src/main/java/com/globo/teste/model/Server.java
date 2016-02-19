package com.globo.teste.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.Document;

import com.mongodb.client.FindIterable;

@XmlRootElement
public class Server {
    public String id;
    public String ip;
    public String name;
    
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Document toDocument () {
        Document doc = new Document();
        doc.put("name", this.getName());
        doc.put("ip", this.getIp());
        return doc;
    }
    
    public static Server fromDocument (Document doc) {
        Server s = new Server();
        s.setIp(doc.getString("ip"));
        s.setName(doc.getString("name"));
        return s;
    }
    
    public static ListWrapper<Server> fromIterable (FindIterable<Document> iterable) {
        ListWrapper<Server> wrapper = new ListWrapper<Server>();
        for (Document doc: iterable) {
            wrapper.list.add(Server.fromDocument(doc));
        }
        return wrapper;
    }
    
}
