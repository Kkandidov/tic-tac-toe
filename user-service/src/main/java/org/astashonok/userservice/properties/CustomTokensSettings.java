package org.astashonok.userservice.properties;

import lombok.Data;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.shared.configurations.YamlPropertySourceFactory;
import org.astashonok.userservice.constants.CustomTokenTypeNames;
import org.astashonok.userservice.models.CustomTokenSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "tokens")
@PropertySource(
        value = CustomTokensSettings.TOKENS_SETTINGS_CLASS_PATH,
        ignoreResourceNotFound = true,
        factory = YamlPropertySourceFactory.class
)
public class CustomTokensSettings {

    public static final String TOKENS_SETTINGS_CLASS_PATH = "tokens-settings.yml";

    private final Map<String, CustomTokenSettings> settings;

    public CustomTokenSettings getAccessTokenSettingsOrDefault() {
        return CommonUtils.getOrDefault(
                settings,
                s -> s.get(CustomTokenTypeNames.ACCESS_TOKEN_TYPE),
                CustomTokenSettings.defaultAccessTokenSettings()
        );
    }

    public CustomTokenSettings getRefreshTokenSettingsOrDefault() {
        return CommonUtils.getOrDefault(
                settings,
                s -> s.get(CustomTokenTypeNames.REFRESH_TOKEN_TYPE),
                CustomTokenSettings.defaultRefreshTokenSettings()
        );
    }
}
