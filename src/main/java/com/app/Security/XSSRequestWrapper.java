package com.app.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.owasp.encoder.Encode;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // Get the original input stream from the wrapped request
        ServletInputStream originalInputStream = super.getInputStream();

        //Read the entire request body into a String
        String requestBody = new String(originalInputStream.readAllBytes());

        //Sanitize the Json body using the sanitizeInput method
        String sanitizedBody = sanitizedInput(requestBody);

        // Create a new ServletInputStream with the sanitized body
        return new ServletInputStream() {

            private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    sanitizedBody.getBytes()
            );

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }
        //Method to sanitize teh input use OWASP Java Encoder
        private String sanitizedInput(String input)
        {
            return Encode.forHtml(input);
        }

}
