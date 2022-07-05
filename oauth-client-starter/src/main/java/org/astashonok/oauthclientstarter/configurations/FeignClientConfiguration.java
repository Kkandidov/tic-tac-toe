package org.astashonok.oauthclientstarter.configurations;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.astashonok.oauthclientstarter.providers.SystemAccessTokenProvider;
import org.javatuples.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfiguration {

    private final SystemAccessTokenProvider systemAccessTokenProvider;

    @Bean
    public RequestInterceptor requestInterceptor(OAuth2AuthorizedClientService clientService) {
        return request -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(OAuth2AuthenticationToken.class::isInstance)
                .map(OAuth2AuthenticationToken.class::cast)
                .map(auth -> Pair.with(auth.getAuthorizedClientRegistrationId(), auth.getName()))
                .<OAuth2AuthorizedClient>map(pair ->
                        clientService.loadAuthorizedClient(pair.getValue0(), pair.getValue1()))
                .map(OAuth2AuthorizedClient::getAccessToken)
                .map(OAuth2AccessToken::getTokenValue)
                .ifPresentOrElse(
                        token -> addAuthHeader(request, token),
                        () -> addAuthHeader(request, systemAccessTokenProvider.getSystemAccessToken().getTokenValue())
                );
    }

    private void addAuthHeader(RequestTemplate requestTemplate, String token) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
