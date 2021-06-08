package com.panimalar.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Nominee extends Person{

	@OneToOne
	@JoinColumn(name = "accountId")
	private Account nomineeAccount;

	public Account getNomineeAccount() {
		return nomineeAccount;
	}

	public void setNomineeAccount(Account nomineeAccount) {
		this.nomineeAccount = nomineeAccount;
	}
	
	
}
