import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.proxy.ProxyServlet;

public class AppSelectorServlet extends ProxyServlet {

        private static final long serialVersionUID = 1L;
        
        protected String rewriteTarget(HttpServletRequest clientRequest){
            
            String uri = clientRequest.getRequestURI().toString();
            String selectedApp = getSelectedApp(clientRequest);
            
            return "http://"+selectedApp+uri;
        }
        
        protected void onProxyResponseSuccess(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, Response serverResponse)
        {
            if (proxyResponse.getHeader("Location") != null && !proxyResponse.getHeader("Location").equals("")) {
                String location = proxyResponse.getHeader("Location");
                try {
                    URL locationUrl = new URL(location);
                    String newUrl = "http://192.168.100.103"+locationUrl.getPath();
                    proxyResponse.setHeader("Location", newUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
              
            }
            
            super.onProxyResponseSuccess(clientRequest, proxyResponse, serverResponse);
        }
        
        private String getSelectedApp (HttpServletRequest clientRequest) {
            String selectedApp = "";
            
            if (clientRequest.getRequestURI().toString().startsWith("/merchant-demo")) {
                selectedApp = "192.168.100.103:7000";
            } else if (clientRequest.getRequestURI().toString().startsWith("/admin-console")) {
                selectedApp = "192.168.100.103:9002";
            }
            
            return selectedApp;
        }

    }