package com.example.demo.auth.utill;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Project ibcs-bof-erp
 * @Author Md. Mizanur Rahman - 598
 * @Since May 28, 2021
 * @version 1.0.0
 */
public class CustomUserDetails implements UserDetails {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetails.class);

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private String email;
	@JsonIgnore
	private String password;

	public CustomUserDetails(Integer id, String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public static CustomUserDetails build(AppUser user) {

		return new CustomUserDetails(user.getId(), user.getUsername(), user.getEmail(),
				user.getPassword());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CustomUserDetails user = (CustomUserDetails) o;
		return Objects.equals(id, user.id);
	}
}
