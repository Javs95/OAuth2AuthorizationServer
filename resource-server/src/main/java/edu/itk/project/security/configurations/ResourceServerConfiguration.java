package edu.itk.project.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.mvcMatcher("/**")
				.authorizeRequests()
				.mvcMatchers("/**")
				.access("hasAuthority('SCOPE_trust')")
				.and()
				.oauth2ResourceServer()
				.jwt();
		return httpSecurity.build();
	}
	
}
