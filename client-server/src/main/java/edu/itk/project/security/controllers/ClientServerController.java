package edu.itk.project.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import edu.itk.project.security.models.Movie;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class ClientServerController {

	@Autowired
	private WebClient webClient;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/")
	public Map<String, String> setSecurityContextHolder(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {		
		String user = oauth2AuthorizedClient.getPrincipalName();
		
		String query = "SELECT authorities.authority FROM users, authorities WHERE users.username = authorities.username AND users.username = ?;";
		String role = (String) jdbcTemplate.queryForObject(query, new Object[] { user }, String.class);
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(role));

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);  
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Map<String, String> json = new LinkedHashMap<>();
		json.put("user", user);
		json.put("role", role);
		
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/movies")
	public List<Movie> getMovies(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/movies")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(List.class)
				.block();
	}
	
	@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/movies/{id}")
	public Movie getMovie(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("id") int id) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/movies/{id}", id)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
				.bodyToMono(Movie.class)
				.block();
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/movies/add")
	public Mono<Movie> addMovie(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, Movie movie) {
		return this.webClient
				.post()
				.uri("http://10.5.0.7:8090/movies/add")
				.body(Mono.just(movie), Movie.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.onStatus(httpStatus -> HttpStatus.BAD_REQUEST.equals(httpStatus), clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)))
				.bodyToMono(Movie.class);
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/movies/edit/{id}")
	public Mono<Movie> editMovie(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, Movie movie, @PathVariable("id") int id) {
		return this.webClient
				.patch()
				.uri("http://10.5.0.7:8090/movies/edit/{id}", id)
				.body(Mono.just(movie), Movie.class)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
				.bodyToMono(Movie.class);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/movies/delete/{id}")
	public Object deleteMovie(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient, @PathVariable("id") int id) {
		return this.webClient
				.delete()
				.uri("http://10.5.0.7:8090/movies/delete/{id}", id)
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.onStatus(httpStatus -> HttpStatus.OK.equals(httpStatus), clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.OK)))
				.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus), clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
				.bodyToMono(Object.class);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
	@RequestMapping("/docs")
	public Object getDocumentation(@RegisteredOAuth2AuthorizedClient("itk-client-authorization-code") OAuth2AuthorizedClient oauth2AuthorizedClient) {
		return this.webClient
				.get()
				.uri("http://10.5.0.7:8090/v3/api-docs")
				.attributes(oauth2AuthorizedClient(oauth2AuthorizedClient))
				.retrieve()
				.bodyToMono(Object.class)
				.block();
	}
	
}
