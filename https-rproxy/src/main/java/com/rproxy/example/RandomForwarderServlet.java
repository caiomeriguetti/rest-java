package com.rproxy.example;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.proxy.ProxyServlet;

import com.rproxy.HostRewriter;

public class RandomForwarderServlet extends ProxyServlet {

        private static final long serialVersionUID = 1L;
        private static final HostRewriter rewriter = new HostRewriter();
        
        protected String rewriteTarget(HttpServletRequest clientRequest){

            String selectedBackend = null;
            
            if (Math.random() > 0.5) {
                selectedBackend = "localhost:7171";
            } else {
                selectedBackend = "localhost:7070";
            }
            
            return rewriter.getRewrited(clientRequest, selectedBackend);
        }

    }