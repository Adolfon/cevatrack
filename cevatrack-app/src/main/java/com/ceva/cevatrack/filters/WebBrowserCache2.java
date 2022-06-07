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

@WebFilter("/c.xhtml") 
public class WebBrowserCache2 implements Filter {
    private static final Logger log = LoggerFactory.getLogger(WebBrowserCache2.class);

    @Override
    public void destroy() {
        log.info(" -- Custom WebBrowserCache2 Web filter destroys method --");

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        log.info(" -- Custom WebBrowserCache2 Web filter for cLS.xhtml starts --");
        HttpServletRequest request = (HttpServletRequest) req;        
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(true);
        String param = request.getParameter("t");
        log.info(" -- Session created from filter --" + session + " ,req URL: " + request.getRequestURL().toString() +
        		"\n response: " + response.getHeaderNames() + " ,URI: " + request.getRequestURI() + " ,paramagter: " + param);        
        response.sendRedirect("cLS.xhtml?t=" + param);
        //chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info(" -- Custom WebBrowserCache2 for cLS.xhtml Web filter init method --");
    }

}
