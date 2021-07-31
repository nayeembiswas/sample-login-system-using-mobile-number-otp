package com.example.demo.auth.controller;

import javax.validation.Valid;

import com.example.demo.auth.repository.AppUserRepository;
import com.example.demo.entity.AppUser;
import com.example.demo.request.SignupRequest;
import com.example.demo.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final PasswordEncoder encoder;
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

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		AppUser user = new AppUser();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	

	
}