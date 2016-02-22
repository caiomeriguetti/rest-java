package com.globo.teste.db;

import com.globo.teste.config.Env;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
 
public class DB {
 
    private static MongoClient mongoClient = null;
    private static MongoDatabase db = null;
    private static final String dbHost = Env.getDbHost();
    private static final int dbPort = Env.getDbPort();
    private static final String dbName = Env.getDbName();
 
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