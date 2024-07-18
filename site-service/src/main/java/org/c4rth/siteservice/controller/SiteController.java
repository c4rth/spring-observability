package org.c4rth.siteservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.c4rth.siteservice.client.UserRestTemplateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import org.c4rth.siteservice.client.UserWebClient;
import org.c4rth.siteservice.model.Site;
import org.c4rth.siteservice.service.SiteService;

/**
 * Sites Controller
 * 
 * @author SayedBaladoh
 *
 */
@RestController
@RequestMapping("/api/sites")
public class SiteController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private UserWebClient userWebClient;
	@Autowired
	private UserRestTemplateClient userRestTemplateClient;

	@PostMapping
	public Site add(@RequestBody Site site) {
		return siteService.save(site);
	}

	@GetMapping
	public List<Site> findAll() {
		return siteService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Optional<Site> site = siteService.get(id);
		if (site.isPresent())
			return new ResponseEntity<>(site.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>("Site not found with id: " + id, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/organization/{organizationId}")
	public List<Site> findByOrganization(@PathVariable("organizationId") Long organizationId) {
		return siteService.getByOrganizationId(organizationId);
	}

	@GetMapping("/organization/{organizationId}/with-users")
	public List<Site> findByOrganizationWithUsers(@PathVariable("organizationId") Long organizationId, @RequestParam(value = "client", defaultValue = "web") String client) {
		List<Site> sites = siteService.getByOrganizationId(organizationId);

		sites.forEach(s -> {
			try {
				if ("web".equalsIgnoreCase(client)) {
					s.setUsers(userWebClient.getUser(s.getId()));
				} else {
					s.setUsers(userRestTemplateClient.getUser(s.getId()));
				}
			} catch (RestClientException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// sites.forEach(s -> s.setUsers(userClient.findBySiteId(s.getId())));
		return sites;
	}
}