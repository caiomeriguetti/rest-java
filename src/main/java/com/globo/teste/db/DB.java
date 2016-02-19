package com.globo.teste.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
 
public class DB {
 
    private static MongoClient mongoClient = null;
    private static MongoDatabase db = null;
    private static final String dbHost = "localhost";
    private static final int dbPort = 27017;
    private static final String dbName = "servers";
 
 public DB(){
 };

 public static MongoDatabase get(){
     if (mongoClient == null) {
         mongoClient = new MongoClient(dbHost , dbPort);
         db = mongoClient.getDatabase(dbName); 
     }
	return db;
 }
}