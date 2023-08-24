package com.cartsy.ecom.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cartsy.ecom.api.v1.model.EcomUser;

import io.jsonwebtoken.Claims;

public class AuthenticatedUserDetails implements UserDetails {

	private HashMap<String, Object> claims = new HashMap<String, Object>();
	
	public AuthenticatedUserDetails(EcomUser authUser) {
		claims.put("USERNAME", authUser.getEcom_username());
		claims.put("ROLE", authUser.getEcom_role());
		claims.put("ID", authUser.getId());
		
	}

	public AuthenticatedUserDetails(Claims claims) {
		
		for(String key: claims.keySet()) {
			this.claims.put(key, claims.get(key));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = (String)claims.get("ROLE");
		
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(role));
		
		return authorities;
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		return (String)claims.get("USERNAME");
	}
	
	public Integer getId() {
		return (Integer)claims.get("ID");
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
