package com.picosoft.picosoft.configuration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picosoft.picosoft.module.User;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager auth) {
		this.authenticationManager=auth;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		User user=null;
		try {
			user= new ObjectMapper().readValue(request.getInputStream(), User.class);
		} 
	
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		org.springframework.security.core.userdetails.User user= (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
		String jwt = Jwts.builder()
				.setSubject(user.getUsername())
				.signWith(SignatureAlgorithm.HS512,SecurityConstant.SECRET)
				.setExpiration(new Date(System.currentTimeMillis()+ SecurityConstant.EXPIRATION_TIME
						))
				.claim("role",user.getAuthorities())
				.compact();
		response.addHeader(SecurityConstant.HEADER_STRING,SecurityConstant.TOKEN_PREFIX+jwt);
				
	}

}
