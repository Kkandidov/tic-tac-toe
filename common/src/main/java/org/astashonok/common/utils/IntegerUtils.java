package org.astashonok.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerUtils {

    public static boolean notNegative(int number) {
        return number >= 0;
    }

    public static boolean moreOrEquals(int comparableNumber, int comparingNumber) {
        return notNegative(NumberUtils.compare(comparableNumber, comparingNumber));
    }
}
