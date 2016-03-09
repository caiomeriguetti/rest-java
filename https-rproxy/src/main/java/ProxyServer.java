import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.http.HttpVersion;
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
	
	public ProxyServer() {
		server = new Server();

		setConnectors();
		
		configureProxyServlet();
		
	}
	
	public void start () throws Exception {
        server.start();
        server.dumpStdErr();
        server.join();
	}
	
	private void setConnectors () {
		
		//HTTP
		HttpConfiguration httpConfig = new HttpConfiguration();
		httpConfig.setSecureScheme("https");
		httpConfig.setSecurePort(443);
		httpConfig.setOutputBufferSize(32768);
		
		ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(httpConfig));
        http.setPort(80);
        http.setIdleTimeout(30000);
        
        //HTTPS
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setStsMaxAge(2000);
        src.setSniHostCheck(true);
        src.setStsIncludeSubDomains(true);
        httpsConfig.addCustomizer(src);
        
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(ProxyServer.class.getResource("keystore.jks").getPath());
        sslContextFactory.setKeyStorePassword("password");
        sslContextFactory.setKeyManagerPassword("password");

        ServerConnector https = new ServerConnector(server, 
        		new SslConnectionFactory(sslContextFactory,HttpVersion.HTTP_1_1.asString()),
        		new HttpConnectionFactory(httpsConfig)
        		);
        https.setPort(443);
        https.setIdleTimeout(500000);

		server.setConnectors(new Connector[] { https, http});
	}
	
	private void configureProxyServlet () {

        Map<String, String> servletInitParams = new HashMap<String, String>();
        servletInitParams.put("maxThreads", "100");
        
        ServletHolder holder = new ServletHolder();
        holder.setInitParameters(servletInitParams);
        holder.setServlet(new AppSelectorServlet());
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(holder, "/*");
        
        server.setHandler(handler);
	}
	
    public static void main(String[] args) throws Exception {
    	ProxyServer s = new ProxyServer();
    	s.start();
    }
}