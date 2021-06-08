package com.panimalar.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long roleId;
	
	private String roleName;
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
}
