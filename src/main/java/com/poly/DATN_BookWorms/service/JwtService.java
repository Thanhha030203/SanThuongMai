package com.poly.DATN_BookWorms.service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.poly.DATN_BookWorms.entities.Account;

@Service
public class JwtService {
//	private static final String Secret_key ="123";
//	public String generateToken(Account account, Collection<SimpleGrantedAuthority> authorities) {
//		Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
//		return JWT.create()
//				.withSubject(account.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis() +50* 60 *1000))
//				.withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//				.sign(algorithm);
//	}
//
//	public String generateRefreshToken(Account account, Collection<SimpleGrantedAuthority> authorities) {
//		Algorithm algorithm = Algorithm.HMAC256(Secret_key.getBytes());
//		return JWT.create()
//				.withSubject(account.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis() +50* 60 *1000))
//				.sign(algorithm);
//	}
}
