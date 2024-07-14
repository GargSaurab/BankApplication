package com.app.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.Security.XSSJsonRequestWrapper;
import com.app.Security.XSSParamRequestWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/* 
 *  FilterChain represents the chain of filters that a request must pass through before reaching the 
 * servlet.
 *  Each filter in the chain can process the request and response objects and decide whether to 
 * pass them along to the next filter or to the target servlet.
 */


 // This filter wraps the request according to the content type of it to there respective classes.
public class XSSFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(XSSFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("In the XSSFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String contentType = httpRequest.getContentType();

        if (contentType != null && contentType.contains("application/json")) {
            // To sanitize Json request
            // It wraps the HttpServletRequest in an XSSJsonRequestWrapper to sanitize the
            // input.
            XSSJsonRequestWrapper jsonWrappedRequest = new XSSJsonRequestWrapper((HttpServletRequest) request);

            // Passes the sanitized request and response objects to the next filter in the
            // chain.
            chain.doFilter(jsonWrappedRequest, response);
        } else {
             // To sanitize the Parameter request
            // It wraps the HttpServletRequest in an XSSParamRequestWrapper to sanitize the
            // input.
            XSSParamRequestWrapper paramWrappedRequest = new XSSParamRequestWrapper((HttpServletRequest) request);

            // Passes the sanitized request and response objects to the next filter in the
            // chain.
            chain.doFilter(paramWrappedRequest, response);
        }

    }
}
