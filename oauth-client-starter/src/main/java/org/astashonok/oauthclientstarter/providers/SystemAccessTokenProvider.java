package org.astashonok.oauthclientstarter.providers;

import org.springframework.security.oauth2.core.OAuth2AccessToken;

public interface SystemAccessTokenProvider {

    OAuth2AccessToken getSystemAccessToken();
}
