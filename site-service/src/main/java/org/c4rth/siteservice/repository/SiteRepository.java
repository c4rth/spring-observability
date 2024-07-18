package org.c4rth.siteservice.repository;

import java.util.List;

import org.c4rth.siteservice.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Site Repository
 * 
 * @author SayedBaladoh
 *
 */
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

	List<Site> findByOrganizationId(Long organizationId);

}