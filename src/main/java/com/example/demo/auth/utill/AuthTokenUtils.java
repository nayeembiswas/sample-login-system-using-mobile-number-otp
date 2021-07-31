package com.example.demo.auth.utill;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.demo.auth.repository.AppUserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class AuthTokenUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthTokenUtils.class);

	private static String JWT_SECRET = "ibcs-bof-erp";
	private static int JWT_EXPIRATION_MS = 86400000;
	
	@Autowired
	AppUserRepository userRepository;
	

	public String generateJwtToken(Authentication authentication) {

		CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

//		System.out.println("id : "+userPrincipal.getId());
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername())) //unable to get subject from UI.
				.claim("userInfo", userRepository.findById(userPrincipal.getId()).get())
//				.claim("username", userPrincipal.getUsername())
//				.claim("appAccess", userRoleAssignRepository.findByAppUserId(userPrincipal.getId()))
				.claim("id", userPrincipal.getId())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getUserIdFromJwtToken(String token) {
		return String.valueOf(Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().get("id"));
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			LOG.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			LOG.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			LOG.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			LOG.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			LOG.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
	
	
	// Start: Use every controller for HTTP request
	public String getTokenFromRequest(HttpServletRequest request) {
		String token = null;
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			token = headerAuth.substring(7, headerAuth.length());
		}
		return token;
	}
	
	public Integer getUserIdFromRequest(HttpServletRequest request) {
		return Integer.valueOf(getUserIdFromJwtToken(getTokenFromRequest(request)));
	}
	// End
}
