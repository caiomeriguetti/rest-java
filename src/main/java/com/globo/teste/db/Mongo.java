package com.globo.teste.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
 
public class Mongo {
 
 private MongoClient mongoClient;
     
 private MongoDatabase db;
  
 private static final String dbHost = "localhost";
 private static final int dbPort = 27017;
 private static final String dbName = "serverApps";
 
 public Mongo(){
	 mongoClient = new MongoClient(dbHost , dbPort);
 };

 public MongoDatabase getDb(){
	return mongoClient.getDatabase(dbName);
 }
}