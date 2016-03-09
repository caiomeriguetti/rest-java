package com.rproxy;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class HostRewriter {
    public String getRewrited (URL url, String rewriteTo) {
        String uri = url.getPath();
        String query = url.getQuery();
        
        return getRewrited(uri, query, rewriteTo);
    }
    
    public String getRewrited (HttpServletRequest clientRequest, String rewriteTo) {
        String uri = clientRequest.getRequestURI().toString();
        String query = clientRequest.getQueryString();
        
        return getRewrited(uri, query, rewriteTo);
    }
    
    public String getRewrited (String uri, String query, String rewriteTo) {
        String newUrl = "http://"+rewriteTo;
        
        if (uri != null && !uri.isEmpty()) {
            newUrl = newUrl + uri;
        }
        
        if (query != null && !query.isEmpty()) {
            newUrl = newUrl + "?" + query;
        }
        return newUrl;
        
    }
}
