package com.cartsy.ecom.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cartsy.ecom.api.v1.model.EcomUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {

	public static final long TOKEN_VALIDITY = 10 * 60 * 60;

	private static String jwtSecret = "my-32-character-ultra-secure-and-ultra-long-secret-this-is-64-characters-or-longer";

	public static String generateJwtToken(EcomUser userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("ROLE", userDetails.getEcom_role());
		claims.put("ID", userDetails.getId());
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getEcom_username())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public static Boolean validateJwtToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		Boolean isTokenExpired = claims.getExpiration().before(new Date());
		return (!isTokenExpired);
	}

	public static String getUsernameFromToken(String token) {
		final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public static Claims getClaimsFromToken(String token) {
		final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims;
	}

}
