package org.astashonok.userservice.constants;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@RequiredArgsConstructor
public enum TimeUnitType {

    MINUTE(Duration::ofMinutes),
    MINUTES(Duration::ofMinutes),
    M(Duration::ofMinutes),
    HOUR(Duration::ofHours),
    HOURS(Duration::ofHours),
    H(Duration::ofHours),
    DAY(Duration::ofDays),
    DAYS(Duration::ofDays),
    D(Duration::ofDays),
    WEEK(time -> Duration.of(time, ChronoUnit.WEEKS)),
    WEEKS(time -> Duration.of(time, ChronoUnit.WEEKS)),
    W(time -> Duration.of(time, ChronoUnit.WEEKS));

    private final Function<Long, Duration> durationFunction;

    public Duration duration(Long time) {
        return durationFunction.apply(time);
    }
}
