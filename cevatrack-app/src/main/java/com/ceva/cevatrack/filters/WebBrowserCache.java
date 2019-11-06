package com.ceva.cevatrack.filters;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@WebFilter(servletNames = {"MyOwnServlet", "UploadServlet"})
@WebFilter("/consignmentSnView.xhtml") // No cache for this page so I can see the right row selected when I move across the browser´s history
public class WebBrowserCache implements Filter {
    private static final Logger log = LoggerFactory.getLogger(WebBrowserCache.class);

    @Override
    public void destroy() {
        log.info(" -- Custom WebBrowserCache Web filter destroys method --");

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        log.info(" -- Custom WebBrowserCache Web filter starts --");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip
                                                                                                                   // JSF
                                                                                                                   // resources
                                                                                                                   // (CSS/JS/Images/etc)
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
        }

        chain.doFilter(req, res);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info(" -- Custom WebBrowserCache Web filter init method --");
    }

}
