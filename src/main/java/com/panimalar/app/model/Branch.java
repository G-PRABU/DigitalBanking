package com.panimalar.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Branch {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long branchId;
	
	private String branchName;
	
	@OneToOne
	@JoinColumn(name = "id")
	private Manager branchManager;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Manager getBranchManager() {
		return branchManager;
	}

	public void setBranchManager(Manager branchManager) {
		this.branchManager = branchManager;
	}
	
}
