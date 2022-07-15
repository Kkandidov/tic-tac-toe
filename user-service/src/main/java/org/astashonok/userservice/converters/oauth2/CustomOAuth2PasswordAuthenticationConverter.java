package org.astashonok.userservice.converters.oauth2;

import org.astashonok.userservice.models.oauth2.CustomOAuth2PasswordAuthenticationToken;
import org.astashonok.userservice.utils.OAuth2EndpointUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CustomOAuth2PasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        return !AuthorizationGrantType.PASSWORD.getValue().equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))
                ? null
                : getAuthentication(request);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        assertsUserNameOrPassword(OAuth2ParameterNames.USERNAME, parameters);
        assertsUserNameOrPassword(OAuth2ParameterNames.PASSWORD, parameters);

        return new CustomOAuth2PasswordAuthenticationToken(
                AuthorizationGrantType.PASSWORD,
                getClientPrincipal(),
                getRequestedScopes(parameters),
                getaAdditionalParameters(parameters)
        );
    }

    private Map<String, Object> getaAdditionalParameters(MultiValueMap<String, String> parameters) {
        return parameters.entrySet().stream()
                .filter(e -> !OAuth2ParameterNames.GRANT_TYPE.equals(e.getKey())
                        && !OAuth2ParameterNames.SCOPE.equals(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));
    }

    private Authentication getClientPrincipal() {
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            throwError(OAuth2ErrorCodes.INVALID_CLIENT);
        }
        return clientPrincipal;
    }

    private Set<String> getRequestedScopes(MultiValueMap<String, String> parameters) {
        assertsParam(
                OAuth2ParameterNames.SCOPE,
                parameters,
                (name, value) -> !(StringUtils.hasText(value) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1)
        );
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        return StringUtils.hasText(scope)
                ? new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")))
                : null;
    }

    private void assertsUserNameOrPassword(String paramName, MultiValueMap<String, String> parameters) {
        assertsParam(
                paramName,
                parameters,
                (name, value) -> StringUtils.hasText(value) && parameters.get(name).size() == 1
        );
    }

    private void assertsParam(String paramName, MultiValueMap<String, String> parameters,
                              BiFunction<String, String, Boolean> condition) {

        String paramValue = parameters.getFirst(paramName);
        if (!condition.apply(paramName, paramValue)) {
            throwError(paramName);
        }
    }

    private void throwError(String parameterName) {
        OAuth2EndpointUtils.throwError(
                OAuth2ErrorCodes.INVALID_REQUEST,
                parameterName,
                OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
    }
}
