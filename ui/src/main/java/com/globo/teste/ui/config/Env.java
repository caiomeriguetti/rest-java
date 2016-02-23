package com.globo.teste.ui.config;

public class Env {
    
    public static String getBackendUrl() {
    	return getEnvVar("SERVERSAPP_BACKEND_URL", "http://localhost:8282/api");
    }
    
    public static String getEnvVar(String name, String defaultValue){
        String value = System.getenv(name);
        
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        return value;
    }

}
