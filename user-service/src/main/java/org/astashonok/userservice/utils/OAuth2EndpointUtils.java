package org.astashonok.userservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2EndpointUtils {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    public static MultiValueMap<String, String> getParameters(@NonNull HttpServletRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        Optional.of(request)
                .map(HttpServletRequest::getParameterMap)
                .ifPresent(params -> params.entrySet().stream()
                        .filter(entry -> entry.getValue() != null && entry.getValue().length > 0)
                        .forEach(entry -> fillParams(parameters, entry.getKey(), entry.getValue())));

        return parameters;
    }

    private static void fillParams(MultiValueMap<String, String> parameters, String paramName, String[] paramValues) {
        for (String value : paramValues) {
            parameters.add(paramName, value);
        }
    }

    public static void throwError(@NonNull String errorCode, @NonNull String parameterName, @NonNull String errorUri) {
        throw new OAuth2AuthenticationException(
                new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri)
        );
    }
}
