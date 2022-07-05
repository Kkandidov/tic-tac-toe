package org.astashonok.clientservice.configurations;

import feign.RequestInterceptor;
import org.javatuples.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login.loginPage("/oauth2/authorization/articles-client-oidc"))
                .oauth2Client(withDefaults());
        return http.build();
    }

    @Bean
    public RequestInterceptor requestInterceptor(OAuth2AuthorizedClientService clientService) {
        return requestTemplate -> {
            Optional.of(SecurityContextHolder.getContext().getAuthentication())
                    .filter(OAuth2AuthenticationToken.class::isInstance)
                    .map(OAuth2AuthenticationToken.class::cast)
                    .map(auth -> Pair.with(auth.getAuthorizedClientRegistrationId(), auth.getName()))
                    .<OAuth2AuthorizedClient>map(pair ->
                            clientService.loadAuthorizedClient(pair.getValue0(), pair.getValue1()))
                    .map(OAuth2AuthorizedClient::getAccessToken)
                    .map(OAuth2AccessToken::getTokenValue)
                    .ifPresent(tokenValue ->
                            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenValue));
        };
    }
}
