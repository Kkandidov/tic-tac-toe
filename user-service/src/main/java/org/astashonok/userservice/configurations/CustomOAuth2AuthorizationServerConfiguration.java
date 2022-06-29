package org.astashonok.userservice.configurations;

import org.astashonok.userservice.converters.oauth2.CustomOAuth2PasswordAuthenticationConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class CustomOAuth2AuthorizationServerConfiguration {

    public static void applyCustomSecurity(HttpSecurity http) throws Exception {
        var authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<HttpSecurity>();

        http.apply(authorizationServerConfigurer.tokenEndpoint((tokenEndpoint) -> tokenEndpoint.accessTokenRequestConverter(
                new DelegatingAuthenticationConverter(List.of(
                        new OAuth2AuthorizationCodeAuthenticationConverter(),
                        new OAuth2RefreshTokenAuthenticationConverter(),
                        new OAuth2ClientCredentialsAuthenticationConverter(),
                        new CustomOAuth2PasswordAuthenticationConverter()))
        )));

//        authorizationServerConfigurer.authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);
//                .and()
//                .apply(new FederatedIdentityConfigurer());
    }
}
