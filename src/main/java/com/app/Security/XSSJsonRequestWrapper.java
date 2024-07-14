package com.app.Security;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/*
 * XSSJsonRequestWrapper acts as a protective layer for incoming Json HTTP requests by sanitizing 
 * the request body to prevent XSS attacks.It ensures that all textual input in JSON request 
 * bodies is cleaned before reaching the application's core logic,thereby enhancing the 
 * security of the web application.
 */

 //Json Sanitizing Class
public class XSSJsonRequestWrapper extends HttpServletRequestWrapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(XSSJsonRequestWrapper.class);

    //Constructs a request object wrapping the given request.
    public XSSJsonRequestWrapper(HttpServletRequest request) {
        super(request);
        
        logger.info("Sanatizing Request json");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        // returns the original input stream from the wrapped request
        // The getInputStream is called here is of ServletRequestWrapper
        // and overrides the SevrvletRequest interface's abstract method.
        ServletInputStream originalInputStream = super.getInputStream();

        // Read the entire request body into a String
        String requestBody = new String(originalInputStream.readAllBytes());

        // Sanitize the Json body using the sanitizeInput method
        String sanitizedBody = sanitizedInput(requestBody);

        // Create a new ServletInputStream with the sanitized body
        return new ServletInputStream() {

            private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    sanitizedBody.getBytes());

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            // This method is used to set an asynchronous read listener on the servlet input
            // stream.
            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    // Method to sanitize the input use OWASP Java Encoder
    private String sanitizedInput(String input) throws JsonMappingException, JsonProcessingException {
        // Parse Json input into JsonNode
        JsonNode json = objectMapper.readTree(input);

        // Sanitize the Json Node
        sanitizeJsonNode(json);

        // Serialize the sanitized Json node back to Json string
        return objectMapper.writeValueAsString(json);

    }

    private void sanitizeJsonNode(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.fieldNames().forEachRemaining(fieldName -> {
                JsonNode field = objectNode.get(fieldName);
                if (field.isTextual()) {
                    // Sanitize text values
                    objectNode.set(fieldName, new TextNode(Encode.forHtml(field.asText())));
                } else {
                    // Recur for nested objects or arrays
                    sanitizeJsonNode(field);
                }
            });
        } else if (node.isArray()) {
            node.forEach(this::sanitizeJsonNode);
        }
    }
}
