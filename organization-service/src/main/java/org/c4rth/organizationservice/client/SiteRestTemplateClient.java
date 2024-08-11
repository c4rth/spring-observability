package org.c4rth.organizationservice.client;

import org.c4rth.organizationservice.model.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

/*~~(component)~~>*/@Component
public class SiteRestTemplateClient {


    @Autowired
    private RestTemplate restTemplate;

    public List<Site> findByOrganization(@PathVariable Long organizationId) {
        String baseUrl = "http://localhost:8082/api/sites/organization/" + organizationId;
        ResponseEntity<List> response = null;
        try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), List.class);
        } catch (Exception ex) {
            System.out.println(ex);
        }
//		System.out.println(response.getBody());
        return response.getBody();
    }

    public List<Site> findByOrganizationWithUsers(@PathVariable Long organizationId) {

        String baseUrl = "http://localhost:8082/api/sites/organization/" + organizationId + "/with-users";
        ResponseEntity<List> response = null;
        try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), List.class);
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