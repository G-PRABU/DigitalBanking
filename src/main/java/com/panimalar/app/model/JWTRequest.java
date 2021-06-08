package com.panimalar.app.model;

import java.io.Serializable;

public class JWTRequest implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -6562186624854535333L;


    private String username;
    private String password;
    
    //default constructor
    public JWTRequest() {
    	
    }
    
    public JWTRequest(String username, String password) {
    	this.password = password;
    	this.username = username;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
