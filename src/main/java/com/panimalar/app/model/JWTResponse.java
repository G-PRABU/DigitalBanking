package com.panimalar.app.model;

import java.io.Serializable;

public class JWTResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4238763570430208748L;

	private final String jwtToken;
	
	private final String authority;
	
	public JWTResponse(String jwtToken,String authority) {
		this.jwtToken = jwtToken;
		this.authority = authority;
	}
	
	public String getJwtToken() {
		return this.jwtToken;
	}
	
	public String getAuthority() {
		return this.authority;
	}
}
