package com.fan2fan.web;

import com.fan2fan.application.Application;
import com.fan2fan.service.Result;
import com.fan2fan.util.Helper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Base class for ControllerTest
 * Created by huangsz on 2014/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class BaseControllerTest {
    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * if there's no this method, an exception would be throwed when running tests
     */
    @Test
    public void test() {}

    protected void basicTest(MockHttpServletRequestBuilder request, Result expected) throws Exception {
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .andExpect(jsonPath(Helper.JSON_RESULT_KEY).value(expected.toString()));
    }
}
