package org.astashonok.oauthclientstarter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2Provider {

    private String authorizationUri;
    private String tokenUri;
    private String jwkSetUri;
    private String issuerUri;
}
