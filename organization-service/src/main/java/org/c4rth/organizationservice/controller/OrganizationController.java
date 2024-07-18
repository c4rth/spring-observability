package org.c4rth.organizationservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.c4rth.organizationservice.client.SiteRestTemplateClient;
import org.c4rth.organizationservice.client.UserRestTemplateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import org.c4rth.organizationservice.client.SiteWebClient;
import org.c4rth.organizationservice.client.UserWebClient;
import org.c4rth.organizationservice.model.Organization;
import org.c4rth.organizationservice.service.OrganizationService;

/**
 * Organizations Controller
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SiteWebClient siteWebClient;

	@Autowired
	private SiteRestTemplateClient siteRestTemplateClient;

	@Autowired
	private UserWebClient userWebClient;

	@Autowired
	private UserRestTemplateClient userRestTemplateClient;

	@PostMapping
	public Organization add(@RequestBody Organization organization) {
		return organizationService.save(organization);
	}

	@GetMapping
	public List<Organization> findAll() {
		return organizationService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Optional<Organization> organization = organizationService.get(id);
		if (organization.isPresent())
			return new ResponseEntity<>(organization.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>("Organization not found with id: " + id, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}/with-sites")
	public Organization findByIdWithSites(@PathVariable("id") Long id, @RequestParam(value = "client", defaultValue = "web") String client) {
		Optional<Organization> organizationOpt = organizationService.get(id);
		Organization organization = null;
		if (organizationOpt.isPresent()) {
			organization = organizationOpt.get();
			if ("web".equalsIgnoreCase(client)) {
				organization.setSites(siteWebClient.findByOrganization(organization.getId()));
			} else {
				organization.setSites(siteRestTemplateClient.findByOrganization(organization.getId()));
			}
		}
		return organization;
	}

	@GetMapping("/{id}/with-sites-with-users")
	public Organization findByIdWithSitesAndUsers(@PathVariable("id") Long id, @RequestParam(value = "client", defaultValue = "web") String client) {

		Optional<Organization> organizationOpt = organizationService.get(id);
		Organization organization = null;
		if (organizationOpt.isPresent()) {
			organization = organizationOpt.get();
			if ("web".equalsIgnoreCase(client)) {
				organization.setSites(siteWebClient.findByOrganizationWithUsers(organization.getId()));
			} else {
				organization.setSites(siteRestTemplateClient.findByOrganizationWithUsers(organization.getId()));
			}
		}
		return organization;
	}

	@GetMapping("/{id}/with-users")
	public Organization findByIdWithUsers(@PathVariable("id") Long id, @RequestParam(value = "client", defaultValue = "web") String client) {
		Optional<Organization> organizationOpt = organizationService.get(id);
		Organization organization = null;
		if (organizationOpt.isPresent()) {
			organization = organizationOpt.get();
			try {
				if ("web".equalsIgnoreCase(client)) {
					organization.setUsers(userWebClient.getUser(organization.getId()));
				} else {
					organization.setUsers(userRestTemplateClient.getUser(organization.getId()));
				}
			} catch (RestClientException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return organization;
	}
}