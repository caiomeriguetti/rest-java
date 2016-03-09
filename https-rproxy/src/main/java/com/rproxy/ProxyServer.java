package com.rproxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;


public class ProxyServer {
	
	private Server server;
	private ProxyServlet proxyServlet;
	private String keystorePath;
	private String keystorePassword;
	private String keystoreManagerPassword;
	
	public ProxyServer(ProxyServlet servlet) {
	    proxyServlet = servlet;
		server = new Server();

	}
	
	public ProxyServer setKeystorePath (String value) {
        keystorePath = value;
        
        return this;
    }
	
	public ProxyServer setKeystorePassword (String value) {
        keystorePassword = value;
        
        return this;
    }
	
	public ProxyServer setKeystoreManagerPassword (String value) {
	    keystoreManagerPassword = value;
        
        return this;
    }
	
	public void start () throws Exception {
	    setConnectors();
        configureProxyServlet(proxyServlet);
        
        server.start();
        server.dumpStdErr();
        server.join();
	}
	
	private void setConnectors () {
		
	    ArrayList<Connector> connectors = new ArrayList<>();
	    
		//HTTP
		HttpConfiguration httpConfig = new HttpConfiguration();
		httpConfig.setSecureScheme("https");
		httpConfig.setSecurePort(443);
		httpConfig.setOutputBufferSize(32768);
		
		ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(httpConfig));
        http.setPort(80);
        http.setIdleTimeout(30000);
        
        connectors.add(0, http);
        
        if (keystorePath != null) {
            //HTTPS
            HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
            SecureRequestCustomizer src = new SecureRequestCustomizer();
            src.setStsMaxAge(2000);
            src.setSniHostCheck(true);
            src.setStsIncludeSubDomains(true);
            httpsConfig.addCustomizer(src);
            
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(keystorePath);
            sslContextFactory.setKeyStorePassword(keystorePassword);
            sslContextFactory.setKeyManagerPassword(keystoreManagerPassword);
    
            ServerConnector https = new ServerConnector(server, 
            		new SslConnectionFactory(sslContextFactory,HttpVersion.HTTP_1_1.asString()),
            		new HttpConnectionFactory(httpsConfig)
            		);
            https.setPort(443);
            https.setIdleTimeout(500000);
            
            connectors.add(0, https);
        }

		server.setConnectors(connectors.toArray(new Connector[connectors.size()]));
	}
	
	private void configureProxyServlet (ProxyServlet servlet) {

        Map<String, String> servletInitParams = new HashMap<String, String>();
        servletInitParams.put("maxThreads", "100");
        
        ServletHolder holder = new ServletHolder();
        holder.setInitParameters(servletInitParams);
        holder.setServlet(servlet);
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(holder, "/*");
        
        server.setHandler(handler);
	}
}