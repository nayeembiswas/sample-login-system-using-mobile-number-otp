package com.example.demo.request;


/**
 * @Project   ibcs-bof-erp
 * @Author    Md. Mizanur Rahman - 598
 * @Since     May 28, 2021
 * @version   1.0.0
 */
public class SignupRequest {

	public String userId;
	public String username;
	public String email;
	public String password;
	
	public SignupRequest() {
	}
	
	
	
	public SignupRequest(String userId, String username, String email, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
