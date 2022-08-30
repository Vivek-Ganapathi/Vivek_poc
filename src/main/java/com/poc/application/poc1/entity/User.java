package com.poc.application.poc1.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "User_Detail")

public class User {
	
	@Id
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "password")
	private String password;
	
	
	

   
	public User(Long userId, String name, String role, String password) {
		this.userId = userId;
		this.name = name;
		this.role = role;
		this.password = password;
	}
	public User() {}
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", role=" + role + ", password=" + password + "]";
	}

	
}
