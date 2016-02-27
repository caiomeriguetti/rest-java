import java.util.HashMap;
import java.util.Map;

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
		
		ServerConnector httpConnector = getHttpConnector();
		ServerConnector httpsConnector = getHttpsConnector();
		
		server.setConnectors(new Connector[] { httpConnector, httpsConnector });
		
		configureProxyServlet();
		
	}
	
	public void start () throws Exception {
        server.start();
        server.dumpStdErr();
        server.join();
	}
	
	private void configureProxyServlet () {

        Map<String, String> servletInitParams = new HashMap<String, String>();
        servletInitParams.put("maxThreads", "100");
        
        ServletHolder holder = new ServletHolder();
        holder.setInitParameters(servletInitParams);
        holder.setServlet(new BackendServlet());
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(holder, "/*");
        
        server.setHandler(handler);
	}
	
	private ServerConnector getHttpConnector () {
        ServerConnector http = new ServerConnector(server);
        http.setPort(8899);
        http.setIdleTimeout(30000);
        
        return http;
	}
	
	private ServerConnector getHttpsConnector () {
		HttpConfiguration httpsConfig = new HttpConfiguration();
        httpsConfig.addCustomizer(new SecureRequestCustomizer());
        
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(ProxyServer.class.getResource("keystore.jks").getPath());
        sslContextFactory.setKeyStorePassword("password");
        sslContextFactory.setKeyManagerPassword("password");

        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(httpsConfig));
        sslConnector.setPort(9998);
        
        return sslConnector;
	}
	
    public static void main(String[] args) throws Exception {
    	ProxyServer s = new ProxyServer();
    	s.start();
    }
}