package com.fan2fan.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for {@link com.fan2fan.util.EnumUtils}
 * @author huangsz
 */
@RunWith(JUnit4.class)
public class EnumUtilsTest {

    private enum TEST_ENUM {FIRST, SECOND, THIRD,}

    @Test
    public void testIsEnum() {
        assertThat(EnumUtils.isEnum(TEST_ENUM.FIRST), equalTo(true));
    }

    @Test
    public void testGetEnumByIndex() {
        Class<?> clazz = TEST_ENUM.FIRST.getClass();
        assertThat(EnumUtils.getEnumByIndex(0, clazz), equalTo(TEST_ENUM.FIRST));
        assertThat(EnumUtils.getEnumByIndex(1, clazz), equalTo(TEST_ENUM.SECOND));
        assertThat(EnumUtils.getEnumByIndex(2, clazz), equalTo(TEST_ENUM.THIRD));
    }

    @Test
    public void testGetIndexByEnum() {
        assertThat(EnumUtils.getIndexByEnum(TEST_ENUM.FIRST), equalTo(0));
        assertThat(EnumUtils.getIndexByEnum(TEST_ENUM.SECOND), equalTo(1));
    }
}
