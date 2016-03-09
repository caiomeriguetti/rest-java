import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.proxy.ProxyServlet;

public class BackendServlet extends ProxyServlet {

        private static final long serialVersionUID = 1L;
        
        protected String rewriteTarget(HttpServletRequest clientRequest){
            
            List<String> backendServers = new ArrayList<String>();
            backendServers.add("localhost:7171");
            backendServers.add("localhost:7070");
            
            String selectedBackend = null;
            
            if (Math.random() > 0.5) {
                selectedBackend = backendServers.get(0);
            } else {
                selectedBackend = backendServers.get(1);
            }
            
            URL url;
            try {
                url = new URL(clientRequest.getRequestURL().toString() + "?" + clientRequest.getQueryString());
                String newUrl = "http://"+selectedBackend+url.getPath();
                if (url.getQuery() != null && !url.getQuery().isEmpty()) {
                    newUrl = newUrl + "?" + url.getQuery();
                }
                return newUrl;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            
            return null;
        }

    }