package com.panimalar.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Authorization {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long authorizationId;
	
	@OneToOne
	@JoinColumn(name = "roleId")
	private Role authorizedRole;
	
	private String authorizationPass;
	
	public void setAuthorizationId(Long authorizationId) {
		this.authorizationId = authorizationId;
	}
	
	public void setAuthorizedRole(Role authorizedRole) {
		this.authorizedRole = authorizedRole;
	}
	
	public void setAuthorizationPass(String authorizationPass) {
		this.authorizationPass = authorizationPass;
	}
	
	public Long getAuthorizationId() {
		return authorizationId;
	}
	
	public Role getAuthorizedRole() {
		return authorizedRole;
	}
	
	public String getAuthorizationPass() {
		return  authorizationPass;
	}
}

