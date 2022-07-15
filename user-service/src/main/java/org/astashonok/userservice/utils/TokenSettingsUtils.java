package org.astashonok.userservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.astashonok.common.utils.CommonUtils;
import org.astashonok.userservice.constants.TimeUnitType;
import org.astashonok.userservice.models.Client;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenSettingsUtils {

    private static final Long DEFAULT_ACCESS_TOKEN_DURATION = 5L;
    private static final Long DEFAULT_REFRESH_TOKEN_DURATION = 60L;

    public static TokenSettings fromClient(@NonNull Client client) {
        Duration accessTokenDuration = getTokenDuration(client, Client::getAccessTokenDuration,
                Client::getAccessTimeUnitType, DEFAULT_ACCESS_TOKEN_DURATION);
        Duration refreshTokenDuration = getTokenDuration(client, Client::getRefreshTokenDuration,
                Client::getRefreshTimeUnitType, DEFAULT_REFRESH_TOKEN_DURATION);

        return TokenSettings.builder()
                .accessTokenTimeToLive(accessTokenDuration)
                .refreshTokenTimeToLive(refreshTokenDuration)
                .build();
    }

    private static Duration getTokenDuration(Client client, Function<Client, Long> durationGetter,
                                             Function<Client, TimeUnitType> timeUnitGetter,
                                             Long defaultDuration) {

        Long duration = CommonUtils.getOrDefault(client, durationGetter, defaultDuration);
        TimeUnitType timeUnitType = CommonUtils.getOrDefault(client, timeUnitGetter, TimeUnitType.MINUTE);

        return timeUnitType.duration(duration);
    }
}
