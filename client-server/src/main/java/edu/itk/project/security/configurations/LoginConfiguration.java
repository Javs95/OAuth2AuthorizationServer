package edu.itk.project.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class LoginConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated()).oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/itk-client-oidc")).oauth2Client(withDefaults());
		return httpSecurity.build();
	}
	
}
