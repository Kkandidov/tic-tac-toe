package org.astashonok.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerUtilsTest {

    @Test
    public void notNegative() {
        assertFalse(IntegerUtils.isNotNegative(-1));
        assertTrue(IntegerUtils.isNotNegative(0));
        assertTrue(IntegerUtils.isNotNegative(5));
    }

    @Test
    public void moreOrEquals() {
        assertTrue(IntegerUtils.moreOrEquals(-1, -1));
        assertTrue(IntegerUtils.moreOrEquals(0, 0));
        assertTrue(IntegerUtils.moreOrEquals(5, 5));

        assertFalse(IntegerUtils.moreOrEquals(-1, 5));
        assertFalse(IntegerUtils.moreOrEquals(0, 5));
        assertFalse(IntegerUtils.moreOrEquals(3, 5));

        assertTrue(IntegerUtils.moreOrEquals(5, -1));
        assertTrue(IntegerUtils.moreOrEquals(5, 0));
        assertTrue(IntegerUtils.moreOrEquals(5, 3));
    }
}