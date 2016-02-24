package com.globo.teste.tests;

public class Env {
    
    public static String getBackendUrl() {
        return getEnvVar("SERVERSAPP_BACKEND_URL", "http://localhost:8282/api");
    }
    
    public static String getTestServerIp() {
        return getEnvVar("SERVERSAPP_TESTSERVER_IP", "54.187.107.64");
    }
    
    public static String getTestServerUsername() {
        return getEnvVar("SERVERSAPP_TESTSERVER_USERNAME", "teste");
    }
    
    public static String getTestServerPassword() {
        return getEnvVar("SERVERSAPP_TESTSERVER_PASSWORD", "1234");
    }
    
    public static String getEnvVar(String name, String defaultValue){
        String value = System.getenv(name);
        
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        return value;
    }

}
