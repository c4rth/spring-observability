package org.c4rth.siteservice.client;

import org.c4rth.siteservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;


@Component
public class UserWebClient {

    @Autowired
    // private RestTemplate restTemplate;
    private WebClient webClient;

    public List<User> getUser(Long siteId) throws RestClientException, IOException {
        String baseUrl = "http://localhost:8081/api/users/site/" + siteId + "?client=web";
        ResponseEntity<List> response = null;
        try {
            //response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), List.class);
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