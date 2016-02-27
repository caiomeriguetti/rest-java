import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.proxy.ProxyServlet;

public class BackendServlet extends ProxyServlet {

        private static final long serialVersionUID = 1L;
        
        protected String rewriteTarget(HttpServletRequest clientRequest){
            
            String uri = clientRequest.getRequestURI().toString();
            
            List<String> backendServers = new ArrayList<String>();
            backendServers.add("localhost:8282");
            backendServers.add("localhost:8181");
            
            String selectedBackend = null;
            
            if (Math.random() > 0.5) {
                selectedBackend = backendServers.get(0);
            } else {
                selectedBackend = backendServers.get(1);
            }
            
            return "http://"+selectedBackend+uri;
        }

    }