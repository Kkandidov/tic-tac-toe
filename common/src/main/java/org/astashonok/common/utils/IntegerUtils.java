package org.astashonok.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerUtils {

    public static boolean isNegative(int number) {
        return number < 0;
    }

    public static boolean lessOrEquals(int comparableNumber, int comparingNumber) {
        return NumberUtils.compare(comparableNumber, comparingNumber) <= 0;
    }
}
