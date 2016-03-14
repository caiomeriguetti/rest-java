package com.globo.teste.config;

public class Env {

    
    public static String getDbHost() {
        return getEnvVar("SERVERSAPP_DB_HOST", "localhost");
    }
    
    public static int getDbPort() {
        return Integer.parseInt(getEnvVar("SERVERSAPP_DB_PORT", "27018"));
    }
    
    public static String getDbName() {
        return getEnvVar("SERVERSAPP_DB_NAME", "servers");
    }
    
    public static String getEnvVar(String name, String defaultValue){
        String value = System.getenv(name);
        
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        return value;
    }

}
