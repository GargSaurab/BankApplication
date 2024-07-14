package com.app.Security;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/*
 * XSSParamRequestWrapper acts as a protective layer for incoming Parameter HTTP requests by
 * sanitizing the request body to prevent XSS attacks.It ensures that all textual input in
 * Parameter request bodies is cleaned before reaching the application's core logic,thereby 
 * enhancing the security of the web application.
 */

//Parameter Sanitizing Class
public class XSSParamRequestWrapper extends HttpServletRequestWrapper {

    private Logger logger = LoggerFactory.getLogger(XSSParamRequestWrapper.class);

    // Constructor to initialize the wrapper with the original request
    public XSSParamRequestWrapper(HttpServletRequest request) {
        super(request);
         
        logger.info("Sanatizing Request paramters");
    }

    // Override the method to get parameter values and sanitize each value
    @Override
    public String[] getParameterValues(String param) {
        // Get the original param values from the wrapper request
        String[] values = super.getParameterValues(param);

        // if the original values are null, return null
        if (values == null) {

            return null;
        }

        // Created a new String array conatining sanitized values
        int count = values.length;
        String[] sanitizedValues = new String[count];

        // Iterates through the original values, sanitizing each one
        for (int i = 0; i < count; i++) {
            sanitizedValues[i] = sanitizedInput(values[i]);
        }

        // return the array of sanitized values
        return sanitizedValues;
    }

    // Method to sanitize the input using OWASP Java Encoder
    private String sanitizedInput(String input) {
        return Encode.forHtml(input);
    }

}
