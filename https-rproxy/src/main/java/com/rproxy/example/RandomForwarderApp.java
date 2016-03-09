package com.rproxy.example;

import com.rproxy.ProxyServer;

public class RandomForwarderApp 
{
    public static void main( String[] args ) throws Exception {
        ProxyServer proxy = new ProxyServer(new RandomForwarderServlet());
        proxy.setKeystorePath(RandomForwarderApp.class.getResource("/keystore.jks").getPath());
        proxy.setKeystorePassword("password");
        proxy.setKeystoreManagerPassword("password");
        proxy.start();
    }
}
