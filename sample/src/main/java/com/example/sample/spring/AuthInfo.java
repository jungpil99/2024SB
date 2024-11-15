package com.example.sample.spring;

import java.time.LocalDateTime;

public class AuthInfo {
	
	
	private Long id;
	private String email;
	private String name;
	private String role;
	private String password;
	
	
	public AuthInfo(Long id, String email, String name, String role, String password) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.role = role;
		this.password = password;
	}


	public Long getId() {
		return id;
	}


	public String getEmail() {
		return email;
	}


	public String getName() {
		return name;
	}

	public String getRole() { return role; }

	public String getPassword() { return password; }
	
	
	
}
