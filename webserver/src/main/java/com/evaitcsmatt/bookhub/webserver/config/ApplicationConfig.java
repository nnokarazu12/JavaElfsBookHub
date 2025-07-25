package com.evaitcsmatt.bookhub.webserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.evaitcsmatt.bookhub.webserver.entities.UserCredential;
import com.evaitcsmatt.bookhub.webserver.repositories.UserCredentialRepository;

@Configuration
public class ApplicationConfig {
	@Autowired
	private UserCredentialRepository userCredentialRepository;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> userCredentialRepository
				.findById(username.toLowerCase())
				.orElseThrow(() -> 
				new UsernameNotFoundException("User with credentials "
						.concat(username)
						.concat(" not found!")));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = 
				new DaoAuthenticationProvider(
						userDetailsService()
						);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
			) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
