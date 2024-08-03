package org.c4rth.siteservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Site Model
 * 
 * @author SayedBaladoh
 *
 */
@Table(name = "sites")
@Entity
public class Site {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "organization_id")
	private Long organizationId;
	
	private String name;
	
	@Transient
	private List<User> users = new ArrayList<>();

	public Site() {
		
	}

	public Site(Long organizationId, String name) {
		super();
		this.organizationId = organizationId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", organizationId=" + organizationId + ", name=" + name + "]";
	}

}