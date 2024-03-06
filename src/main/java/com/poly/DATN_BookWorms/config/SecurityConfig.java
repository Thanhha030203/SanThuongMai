package com.poly.DATN_BookWorms.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



	// @Bean
	// public RedirectStrategy redirectStrategy() {
	// 	return new DefaultRedirectStrategy() {
	// 		public String getLocation(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	// 			String previousUrl = request.getHeader("Referer");
	// 			if (previousUrl != null) {
	// 				return previousUrl;
	// 			} else {
	// 				return "/Ibook/index";
	// 			}
	// 		}
	// 	};
	// }

	// @Bean
	// public AuthenticationSuccessHandler successHandler() {
	// 	return new AuthenticationSuccessHandler() {
	// 		@Override
	// 		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	// 			redirectStrategy().sendRedirect(request, response, "/Ibook/index");
	// 		}
	// 	};
	// }
	//	Phân quyền sử dụng
	@Bean
	public RedirectStrategy redirectStrategy() {
		return new DefaultRedirectStrategy() {
			public String getLocation(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
				String previousUrl = request.getHeader("Referer");
				if (previousUrl != null) {
					return previousUrl;
				} else {
					return "/Ibook/index";
				}
			}
		};
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				redirectStrategy().sendRedirect(request, response, "/Ibook/index");
			}
		};
	}
	//	Phân quyền sử dụng
	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((request) -> request
				.requestMatchers("/account/**", "/signin/**", "/signup/**", "/product/**", "/Admin/**","/Ibook/index","/Ibook/header").permitAll()
				.requestMatchers("rest/**").permitAll()
				.requestMatchers("/Client/**")
				.permitAll()
				.requestMatchers("static/**")
				.permitAll()
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.anyRequest().authenticated());
		http.formLogin(form -> form.loginPage("/account/login")
				.loginProcessingUrl("/account/login")
				.successHandler(successHandler())
				.permitAll());

		http.oauth2Login(customize -> customize.loginPage("/account/login")
				.defaultSuccessUrl("/account/login-google/success").defaultSuccessUrl("/account/login-facebook/success")
				.failureUrl("/account/login")
				.authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
						.baseUri("/oauth2/authorization"))
				.permitAll());

		http.logout((form) -> form
				.logoutUrl("/account/logout")
				.logoutSuccessUrl("/account/login")
				.permitAll());

		http.csrf().disable();
		http.cors();
		return http.build();
	}


	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://sandbox.vnpayment.vn");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("Origin");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("Authorization");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
