import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.proxy.ProxyServlet;

public class AppSelectorServlet extends ProxyServlet {

        private static final long serialVersionUID = 1L;
        
        protected String rewriteTarget(HttpServletRequest clientRequest){
            
            String selectedApp = getSelectedApp(clientRequest);
            URL url;
            try {
                url = new URL(clientRequest.getRequestURL().toString() + "?" + clientRequest.getQueryString());
                String newUrl = "http://"+selectedApp+url.getPath();
                if (url.getQuery() != null && !url.getQuery().isEmpty()) {
                    newUrl = newUrl + "?" + url.getQuery();
                }
                return newUrl;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        protected void onProxyResponseSuccess(HttpServletRequest clientRequest, HttpServletResponse proxyResponse, Response serverResponse)
        {
            if (proxyResponse.getHeader("Location") != null && !proxyResponse.getHeader("Location").equals("")) {
                String location = proxyResponse.getHeader("Location");
                try {
                    URL locationUrl = new URL(location);
                    String newUrl = "http://192.168.100.103"+locationUrl.getPath();
                    if (locationUrl.getQuery() != null && !locationUrl.getQuery().isEmpty()) {
                        newUrl = newUrl + "?" + locationUrl.getQuery();
                    }
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
            } else if (clientRequest.getRequestURI().toString().startsWith("/start")) {
                selectedApp = "192.168.100.103:8000";
            } else if (clientRequest.getRequestURI().toString().startsWith("/step")) {
                selectedApp = "192.168.100.103:9000";
            }
            
            return selectedApp;
        }

    }