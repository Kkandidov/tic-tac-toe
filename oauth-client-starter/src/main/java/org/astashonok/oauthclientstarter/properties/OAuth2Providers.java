package org.astashonok.oauthclientstarter.properties;

import lombok.Data;
import org.astashonok.oauthclientstarter.model.OAuth2Provider;
import org.astashonok.shared.configurations.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "register")
@PropertySource(
        value = {
                OAuth2Providers.DEFAULT_REGISTRATION_PROVIDERS_CLASS_PATH,
                OAuth2Providers.REGISTRATION_PROVIDERS_CLASS_PATH
        },
        ignoreResourceNotFound = true,
        factory = YamlPropertySourceFactory.class
)
public class OAuth2Providers {

    public static final String DEFAULT_REGISTRATION_PROVIDERS_CLASS_PATH = "default-register-providers.yml";
    public static final String REGISTRATION_PROVIDERS_CLASS_PATH = "register-providers.yml";

    private final Map<String, OAuth2Provider> providers;
}