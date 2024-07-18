package org.c4rth.organizationservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CopyHeaderInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CopyHeaderInterceptor.class);

    private final Set<String> commonHeaders;

    public CopyHeaderInterceptor(Set<String> commonHeaders) {
        this.commonHeaders = commonHeaders;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.info("intercept");
        return execution.execute(copyHeaders(request), body);
    }

    private HttpRequest copyHeaders(HttpRequest request) {
        request.getHeaders().keySet().forEach(headerName -> logger.info("copyHeaders: " + headerName));
        request.getHeaders().keySet().stream()
                .filter(headerName -> commonHeaders.contains(headerName.toLowerCase()))
                .forEach(headerName -> {
                    logger.info("copyHeaders: add " + headerName);
                    request.getHeaders().add(headerName, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(headerName));
                });
        return request;
    }

}
