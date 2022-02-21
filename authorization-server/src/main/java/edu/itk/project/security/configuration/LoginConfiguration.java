package edu.itk.project.security.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class LoginConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	SecurityFilterChain loginSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login");
		return httpSecurity.build();
	}
	
}
