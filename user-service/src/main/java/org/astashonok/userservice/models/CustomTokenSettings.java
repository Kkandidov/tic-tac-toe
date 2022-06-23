package org.astashonok.userservice.models;

import lombok.*;
import org.astashonok.userservice.constants.TimeUnitType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomTokenSettings {

    private static final Long DEFAULT_ACCESS_TOKEN_DURATION = 5L;
    private static final Long DEFAULT_REFRESH_TOKEN_DURATION = 60L;

    @NonNull
    private Long duration;

    @NonNull
    private TimeUnitType timeUnitType;

    public static CustomTokenSettings defaultAccessTokenSettings() {
        return new CustomTokenSettings(DEFAULT_ACCESS_TOKEN_DURATION, TimeUnitType.MINUTE);
    }

    public static CustomTokenSettings defaultRefreshTokenSettings() {
        return new CustomTokenSettings(DEFAULT_REFRESH_TOKEN_DURATION, TimeUnitType.MINUTE);
    }
}
