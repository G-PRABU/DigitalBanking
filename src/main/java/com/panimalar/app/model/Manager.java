package com.panimalar.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Manager extends Person{

	@OneToOne
	@JoinColumn(name = "authorizationId")
	private Authorization authorization;

	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
	
}
