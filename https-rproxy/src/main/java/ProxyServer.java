import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class ProxyServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server();

        // HTTP connector
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8899);
        http.setIdleTimeout(30000);
 
        // Set the connector
        server.addConnector(http);
 
        Map<String, String> servletInitParams = new HashMap<String, String>();
        servletInitParams.put("maxThreads", "10");
        
        ServletHolder holder = new ServletHolder();
        holder.setInitParameters(servletInitParams);
        holder.setServlet(new BackendServlet());
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(holder, "/*");
        
        // Set a handler
        server.setHandler(handler);

        server.start();
        server.dumpStdErr();
        server.join();

    }
    
    public static class BackendServlet extends ProxyServlet {

        private static final long serialVersionUID = 1L;
        
        protected String rewriteTarget(HttpServletRequest clientRequest){
            
            String uri = clientRequest.getRequestURI().toString();
            
            List<String> backendServers = new ArrayList<String>();
            backendServers.add("localhost:8282");
            backendServers.add("localhost:8383");
            
            String selectedBackend = null;
            
            if (Math.random() > 0.5) {
                selectedBackend = backendServers.get(0);
            } else {
                selectedBackend = backendServers.get(1);
            }
            
            return "http://"+selectedBackend+uri;
        }

    }
}