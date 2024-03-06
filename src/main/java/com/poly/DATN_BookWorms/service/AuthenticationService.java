package com.poly.DATN_BookWorms.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
//		private final AccountRepo accountRepo;
//		private final AuthenticationManager authenticationManager;
//		private final RoleRepo roleRepo;
//		private final AuthoritiesRepo authoritiesRepo;
//		private final JwtService jwtService;
//		
//		public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
//			Account account = accountRepo.findByUsername(authenticationRequest.getUsername()).orElseThrow();
//			List<Roles> roles = null;
//			if(account!=null) {
//			List<Authorities> authorities = (List<Authorities>) account.getAuthorities();
//				for (int i = 0; i < authorities.size(); i++) {
//					roles.add(authorities.get(i).getRoles());
//				}
//			}
//			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//			Set<Roles> set = new HashSet<>();
//			roles.stream().forEach(c ->set.add(new Roles(c.getRolename())));
//			
//			for (int i = 0; i < roles.size(); i++) {
//				account.setAuthorities(roles.get(i).getListOfAuthorities());
//			}
//			set.stream().forEach(i ->authorities.add(new SimpleGrantedAuthority(i.getRolename())));
//			var jwtToken = jwtService.generateToken(account, authorities);
//			var jwtRefreshToken = jwtService.generateRefreshToken(account, authorities);
//			return AuthenticationResponse.builder().token(jwtToken).refeshToken(jwtRefreshToken).build();
//		}
}
