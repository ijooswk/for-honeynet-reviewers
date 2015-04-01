package com.fan2fan.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Test class for {@link org.apache.catalina.security.SecurityUtil}
 * @author huangsz
 */
@RunWith(JUnit4.class)
public class SecurityUtilsTest {

    private String[] input = new String[]{"122", "", "撒地方sdf", "xcwe"};

    @Test
    public void testEncoderByMD5() {
        final int length = 32;
        for (String s: input) {
            assertThat(SecurityUtils.encoderByMd5(s).length(),
                    equalTo(length));
        }
        String encoded = SecurityUtils.encoderByMd5(input[0]);
        assertThat(encoded, equalTo(SecurityUtils.encoderByMd5(input[0])));
    }

    @Test
    public void testEncoderBySha512() {
        final int length = 128;
        for (String s: input) {
            assertThat(SecurityUtils.encoderBySha512(s).length(),
                    equalTo(length));
        }
        String encoded = SecurityUtils.encoderBySha512(input[0]);
        assertThat(encoded, equalTo(SecurityUtils.encoderBySha512(input[0])));
    }

    @Test
    public void testGenToken() {
        final int length = 36;
        assertThat(SecurityUtils.genToken().length(), equalTo(length));
        assertThat(SecurityUtils.genToken(), not(SecurityUtils.genToken()));
    }
}
