package com.ceva.cevatrack.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter("/techNetLK.xhtml") 
public class WebBrowserCacheTechnet implements Filter {
    private static final Logger log = LoggerFactory.getLogger(WebBrowserCacheTechnet.class);

    @Override
    public void destroy() {
        log.info(" -- Custom WebBrowserCache for TechNet link Web filter destroys method --");

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        log.info(" -- Custom WebBrowserCache Web filter for techNetLink.xhtml starts --");
        HttpServletRequest request = (HttpServletRequest) req;        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(true);
        String param = request.getParameter("key");             
        log.info(" -- Session created from filter --" + session + " ,req URL: " + request.getRequestURL().toString() +
        		"\n response: " + response.getHeaderNames() + " ,URI: " + request.getRequestURI() + " ,paramagter: " + param);        
        response.sendRedirect("techNetLink.xhtml?key=" + param);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info(" -- Custom WebBrowserCache for techNetLink.xhtml Web filter init method --");
    }

}
