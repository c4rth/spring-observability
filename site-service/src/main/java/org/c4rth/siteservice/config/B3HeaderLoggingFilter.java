package org.c4rth.siteservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class B3HeaderLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(B3HeaderLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Log incoming B3 headers
        logB3Headers(httpRequest, "Incoming");

        chain.doFilter(request, response);

        // Log outgoing B3 headers
        logB3Headers(httpResponse, "Outgoing");
    }

    private void logB3Headers(HttpServletRequest request, String direction) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            //if (headerName.startsWith("X-B3-")) {
                logger.info("{} B3 Header: {}={}", direction, headerName, request.getHeader(headerName));
            //}
        }
    }

    private void logB3Headers(HttpServletResponse response, String direction) {
        for (String headerName : response.getHeaderNames()) {
            //if (headerName.startsWith("X-B3-")) {
                logger.info("{} B3 Header: {}={}", direction, headerName, response.getHeader(headerName));
            //}
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }
}
