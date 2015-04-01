package com.fan2fan.web.views.users.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fan2fan.model.User;
import com.fan2fan.web.BaseControllerTest;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test for {@link com.fan2fan.web.views.users.controller.ChangePasswordController}
 * @author huangsz
 */
public class ChangePasswordControllerTest extends BaseResourceControllerTest {

    private User user;

    @Before
    public void setUp() {
        super.setUp();
        user = createCountryManager();
        setUpSession(user);
    }

    @Test
    public void testShowChangePasswordPage() throws Exception {
        MockHttpServletRequestBuilder request = get("/users/edit/password/");
        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.view().name("user/changePassword"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("changePwdCommand"))
                .andReturn();
//        result.getModelAndView().getModel().get("changePwdCommand")
    }
}
