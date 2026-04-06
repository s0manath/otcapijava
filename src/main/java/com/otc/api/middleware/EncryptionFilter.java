package com.otc.api.middleware;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class EncryptionFilter extends OncePerRequestFilter {

    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper;

    @Autowired
    public EncryptionFilter(EncryptionUtils encryptionUtils, ObjectMapper objectMapper) {
        this.encryptionUtils = encryptionUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/swagger-ui/") || requestURI.contains("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpServletRequest requestToUse = request;
        
        // Decrypt Request Body if POST
        if ("POST".equalsIgnoreCase(request.getMethod()) && request.getContentType() != null && request.getContentType().contains("application/json")) {
            byte[] body = request.getInputStream().readAllBytes();
            if (body.length > 0) {
                try {
                    JsonNode node = objectMapper.readTree(body);
                    if (node.has("encryptedata")) {
                        String encryptedData = node.get("encryptedata").asText();
                        String decryptedData = encryptionUtils.decrypt(encryptedData);
                        requestToUse = new DecryptedRequestWrapper(request, decryptedData.getBytes(StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {
                    // Log error and continue with original request if decryption fails
                }
            }
        }

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestToUse, responseWrapper);

        // Encrypt Response Body if POST
        if ("POST".equalsIgnoreCase(request.getMethod()) && responseWrapper.getContentType() != null && responseWrapper.getContentType().contains("application/json")) {
            byte[] responseArray = responseWrapper.getContentAsByteArray();
            if (responseArray.length > 0) {
                try {
                    String plainResponse = new String(responseArray, StandardCharsets.UTF_8);
                    String encryptedResponse = encryptionUtils.encrypt(plainResponse);
                    
                    ObjectNode wrappedResponse = objectMapper.createObjectNode();
                    wrappedResponse.put("encryptedata", encryptedResponse);
                    
                    byte[] finalResponse = objectMapper.writeValueAsBytes(wrappedResponse);
                    response.setContentLength(finalResponse.length);
                    response.getOutputStream().write(finalResponse);
                    return;
                } catch (Exception e) {
                    // Log error and fallback
                }
            }
        }
        
        responseWrapper.copyBodyToResponse();
    }

    private static class DecryptedRequestWrapper extends HttpServletRequestWrapper {
        private final byte[] body;

        public DecryptedRequestWrapper(HttpServletRequest request, byte[] body) {
            super(request);
            this.body = body;
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }
            };
        }

        @Override
        public int getContentLength() {
            return body.length;
        }

        @Override
        public long getContentLengthLong() {
            return body.length;
        }
    }
}
