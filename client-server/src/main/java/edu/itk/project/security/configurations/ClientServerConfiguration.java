package edu.itk.project.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ClientServerConfiguration {

	@Bean
	WebClient webClient(OAuth2AuthorizedClientManager oauth2AuthorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction servletOAuth2AuthorizedClientExchangeFilterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(oauth2AuthorizedClientManager);
		return WebClient.builder()
				.apply(servletOAuth2AuthorizedClientExchangeFilterFunction.oauth2Configuration())
				.build();
	}
	
	@Bean
	OAuth2AuthorizedClientManager oauth2AuthorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository oauth2AuthorizedClientRepository) {
		OAuth2AuthorizedClientProvider oauth2AuthorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.authorizationCode()
				.refreshToken()
				.build();
		DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oauth2AuthorizedClientRepository);
		defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(oauth2AuthorizedClientProvider);
		return defaultOAuth2AuthorizedClientManager;
	}
	
}
