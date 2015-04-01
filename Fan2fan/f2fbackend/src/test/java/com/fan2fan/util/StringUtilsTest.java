package com.fan2fan.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author huangsz
 */
@RunWith(JUnit4.class)
public class StringUtilsTest {

    @Test
    public void testGetWhiteSpaces() {
        String spaces = StringUtils.getWhiteSpaces();
        for (Character c : spaces.toCharArray()) {
            assertThat(Character.isWhitespace(c), equalTo(true));
        }
        assertThat(spaces.indexOf(' ') >= 0, equalTo(true));
    }

    @Test
    public void testTrimWith() {
        String trimoff = StringUtils.getWhiteSpaces().concat("/");
        String s = "  / abc ";
        assertThat(StringUtils.trimWith(s, trimoff), equalTo("abc"));
        s = "abc";
        assertThat(StringUtils.trimWith(s, trimoff), equalTo("abc"));
        s = " abc  ";
        assertThat(StringUtils.trimWith(s, trimoff), equalTo("abc"));
        s = "  abc/ ";
        assertThat(StringUtils.trimWith(s, trimoff), equalTo("abc"));
        s = "abc /";
        assertThat(StringUtils.trimWith(s, trimoff), equalTo("abc"));

    }

}
