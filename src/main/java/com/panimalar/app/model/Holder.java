package com.panimalar.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Holder extends Person {

	@OneToOne
	@JoinColumn(name = "authorizationId")
	private Authorization authorization;
	
	@OneToOne
	@JoinColumn(name = "accountId")
	private Account holderAccount;

	private Long holderAadhar;
	
	private String holderPanCard;
	
	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}

	public Account getHolderAccount() {
		return holderAccount;
	}

	public void setHolderAccount(Account holderAccount) {
		this.holderAccount = holderAccount;
	}

	public Long getHolderAadhar() {
		return holderAadhar;
	}

	public void setHolderAadhar(Long holderAadhar) {
		this.holderAadhar = holderAadhar;
	}

	public String getHolderPanCard() {
		return holderPanCard;
	}

	public void setHolderPanCard(String holderPanCard) {
		this.holderPanCard = holderPanCard;
	}
	
	
	
	
}

