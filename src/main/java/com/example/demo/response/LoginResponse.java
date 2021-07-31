package com.example.demo.response;


import lombok.Data;

/**
 * @Project   ibcs-bof-erp
 * @Author    Md. Mizanur Rahman - 598
 * @Since     May 28, 2021
 * @version   1.0.0
 */

@Data
public class LoginResponse {

	public String token;
	public Integer id;
	
	public LoginResponse(String token, 
			Integer id) {
		super();
		this.token = token;
		this.id = id;
	}
	
	
	
}
