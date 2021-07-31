package com.example.demo.auth.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.utill.AuthTokenUtils;
import com.example.demo.auth.utill.CustomUserDetails;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.CommonResponse;
import com.example.demo.response.LoginResponse;

import lombok.AllArgsConstructor;

/**
 * @Project   ibcs-bof-erp
 * @Author    Md. Mizanur Rahman - 598
 * @Since     May 28, 2021
 * @version   1.0.0
 */

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthenticationManager authenticationManager;


	private final AuthTokenUtils authTokenUtils;
//	private PasswordEncoder encoder;
//	private final AppUserRepository appUserRepository;

	@PostMapping("signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = authTokenUtils.generateJwtToken(authentication);

			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			
			/* get user role */
			
			/* get approval team */
			
			/* get password history */
			
			/* get password policy */
//			AppUser appUser = appUserRepository.findById(userDetails.getId()).get();
			
			/* create response */
			CommonResponse res  = new CommonResponse();
			res.setStatus(true);
			res.setData(new LoginResponse(jwtToken, userDetails.getId()));
			return ResponseEntity.ok(res);
			
		}catch (Exception e) {
			e.printStackTrace();
			CommonResponse res  = new CommonResponse();
			res.setStatus(false);
			res.setMessage("Wrong email or password");
			return ResponseEntity.ok(res);
		}
		

	}
	

	
}