package org.astashonok.userservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.astashonok.userservice.constants.TimeUnitType;
import org.astashonok.userservice.models.CustomTokenSettings;
import org.astashonok.userservice.properties.CustomTokensSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenSettingsUtils {

    public static TokenSettings fromCustomTokensSettings(@NonNull CustomTokensSettings settings) {
        return TokenSettings.builder()
                .accessTokenTimeToLive(getTokenDuration(settings.getAccessTokenSettingsOrDefault()))
                .refreshTokenTimeToLive(getTokenDuration(settings.getRefreshTokenSettingsOrDefault()))
                .build();
    }

    private static Duration getTokenDuration(CustomTokenSettings settings) {
        Long duration = settings.getDuration();
        TimeUnitType timeUnitType = settings.getTimeUnitType();

        return timeUnitType.duration(duration);
    }
}
