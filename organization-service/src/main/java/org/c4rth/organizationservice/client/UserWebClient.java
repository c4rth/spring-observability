package org.c4rth.organizationservice.client;

import org.c4rth.organizationservice.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

/*~~(component)~~>*/@Component
public class UserWebClient {

    private final WebClient webClient;

    public UserWebClient(WebClient organizationWebClient) {
        this.webClient = organizationWebClient;
    }

    public List<User> getUser(Long organizationId) throws RestClientException, IOException {
        String baseUrl = "http://localhost:8081/api/users/organization/" + organizationId +"?client=web";
        ResponseEntity<List> response = null;
        try {
            response = webClient.get().uri(baseUrl).retrieve().toEntity(List.class).block();
        } catch (Exception ex) {
            System.out.println(ex);
        }
//		System.out.println(response.getBody());
        return response.getBody();
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}