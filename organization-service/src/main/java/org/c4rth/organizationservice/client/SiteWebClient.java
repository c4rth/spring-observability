package org.c4rth.organizationservice.client;

import org.c4rth.organizationservice.model.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

@Component
public class SiteWebClient {


    @Autowired
    // private RestTemplate restTemplate;
    private WebClient webClient;

    public List<Site> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        String baseUrl = "http://localhost:8082/api/sites/organization/" + organizationId + "?client=web";
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

    public List<Site> findByOrganizationWithUsers(@PathVariable("organizationId") Long organizationId) {

        String baseUrl = "http://localhost:8082/api/sites/organization/" + organizationId + "/with-users" + "?client=web";
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