package com.fan2fan.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Timestamp;
import java.util.UUID;

@RunWith(JUnit4.class)
public class PasswordUtilsTest {

    /**
     * Test methods for {@link com.fan2fan.util.PasswordUtils#createPassword(String, String)}
     * and {@link com.fan2fan.util.PasswordUtils#authenticatePassword(String, String, String)}
     */
    @Test
    public void testCreatePwdAndAuthenticate() {
        String origin = UUID.randomUUID().toString();
        String salt = new java.sql.Time(System.currentTimeMillis()).toString();
        String encoded = PasswordUtils.createPassword(origin, salt);
        assertThat(encoded, equalTo(PasswordUtils.createPassword(origin, salt)));
        assertThat(PasswordUtils.authenticatePassword(origin, salt, encoded), is(true));

        final int length = 32;
        String input[] = {"sdfasdfas", "的撒发顺丰sdfsa", ""};
        for (String s: input) {
            assertThat(PasswordUtils.createPassword(s, "" + System.currentTimeMillis()).length(), equalTo(length));
        }
    }

}
