package com.fan2fan.web.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fan2fan.dao.UserDao;
import com.fan2fan.form.UpdatePasswordForm;
import com.fan2fan.model.User;
import com.fan2fan.model.UserDetail;
import com.fan2fan.model.UserToken;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import com.fan2fan.util.Helper;
import com.fan2fan.util.PasswordUtils;
import com.fan2fan.util.SecurityUtils;
import com.fan2fan.util.TimeUtils;
import com.fan2fan.web.BaseResourceControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.sql.Timestamp;

/**
 * Integration Test class for {@link com.fan2fan.web.api.AccountController}
 *
 * @author huangsz
 */
public class AccountControllerTest extends BaseResourceControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseResourceControllerTest.class);

    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private static final String password = "huangsz";

    @Before
    public void setUp() {
        super.setUp();
        user = new User();
        user.setUserName("Michael");
        user.setEmail("xxx@xxx.com");
        user.setNotifiable(false);
        user.setPassword(password);
    }

    @Test
    @Rollback(true)
    public void testSignup() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/account/signup")
                .contentType(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        // normal test
        user.setEmail(System.currentTimeMillis() + "@f2f.com");
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.SUCCESS);
    }

    @Test
    @Rollback(true)
    public void testSignin() throws Exception {
        UserToken userToken = signUp(user);

        MockHttpServletRequestBuilder request = post("/api/account/signin")
                .contentType(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        user.setPassword(password);

        // normal case
//        activate(user.getId(), userToken.getToken());
        request.content(Helper.jsonMapper.writeValueAsString(user));
        String result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath(Helper.JSON_RESULT_KEY).value(Result.SUCCESS.toString()))
                .andReturn().getResponse().getContentAsString();
        User returned = Helper.getReturnJsonObject(result, User.class);
        assertThat(returned.getId(), equalTo(user.getId()));
        assertThat(returned.getUserName(), equalTo("Michael"));
        assertThat(returned.isNotifiable(), equalTo(false));

        // wrong password
        user.setPassword("wrongpassword");
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.FAIL);

        // not exist
        user.setEmail("notexist@fan2fan.com");
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.NOTEXISTS);
    }

    @Test
    @Rollback(true)
    public void testChangePassword() throws Exception {
        user.setPassword(password);
        UserToken userToken = signUp(user);

        long userId = user.getId();
        UpdatePasswordForm form = new UpdatePasswordForm();
        form.setNewPassword("newpassword");

        MockHttpServletRequestBuilder request = post(String.format("/api/account/%d/password", userId))
                .contentType(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        // failed, namely input the wrong old password
        activate(user.getId(), userToken.getToken());
        form.setPassword("wrongpassword");
        request.content(Helper.jsonMapper.writeValueAsString(form));
        basicTest(request, Result.FAIL);

        // success
        form.setPassword(password);
        request.content(Helper.jsonMapper.writeValueAsString(form));
        basicTest(request, Result.SUCCESS);
    }

    @Test
    @Rollback(true)
    public void testForgetAndResetPassword() throws Exception {
        MockHttpServletRequestBuilder request = post(String.format("/api/account/forgetpassword"));
        Helper.setRequestAllJsonType(request);

        // forget password request: not exists
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.NOTEXISTS);

        // forget password request: success
        signUp(user);
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.SUCCESS);

        // reset password and test
        final String newPwd = "newpassword";
        user.setPassword(newPwd);
        UserToken userToken = createToken(user);
        request = post(String.format("/api/account/%d/resetpassword/%s", user.getId(), userToken.getToken()));
        Helper.setRequestAllJsonType(request);
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.SUCCESS);
        assertThat(PasswordUtils.createPassword(newPwd, PasswordUtils.getUserSalt(user)),
                equalTo(userDao.getPassword(user.getId())));
    }

    @Test
    @Rollback(true)
    public void testGetAndUpdateUserDetail() throws Exception {
        signUp(user);

        // get userDetail
        MockHttpServletRequestBuilder request = get(String.format("/api/account/%d", user.getId()))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));
        mockMvc.perform(request).andExpect(jsonPath("$.gender").value("FEMALE"))
                .andExpect(jsonPath("$.userName").value("Michael"))
                .andExpect(jsonPath("$.type").value("END_USER"));

        request = post(String.format("/api/account/%d", user.getId()))
                .contentType(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(Helper.APPLICATION_JSON_CHARSET_UTF_8));

        UserDetail detail = new UserDetail();
        detail.setPhone("13422355323");
        detail.setDesc("description");
        detail.setFullName("huangsz");
        detail.setUserName("xiaozhi");
        detail.setProvince("Fujian");
        detail.setAvatarUrl("");
        // update userDetail
        request.content(Helper.jsonMapper.writeValueAsString(detail));
        basicTest(request, Result.SUCCESS);

        detail = userService.getUserDetail(user.getId());
        assertThat(detail.getDesc(), equalTo("description"));
        assertThat(detail.getPhone(), equalTo("13422355323"));
        assertThat(detail.getFullName(), equalTo("huangsz"));
        assertThat(detail.getUserName(), equalTo("xiaozhi"));
        assertThat(detail.getProvince(), equalTo("Fujian"));
    }

    @Test
    @Rollback(true)
    public void testCheckUser() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/account/signup");
        Helper.setRequestAllJsonType(request);
        request.content(Helper.jsonMapper.writeValueAsString(user));
        basicTest(request, Result.SUCCESS);

        User checked = new User();
        checked.setEmail(user.getEmail());
        request = post("/api/account/registered");
        Helper.setRequestAllJsonType(request);
        request.content(Helper.jsonMapper.writeValueAsString(checked));
        mockMvc.perform(request)
                .andExpect(jsonPath(Helper.JSON_RESULT_KEY).value(true));

        checked.setEmail("another@mail.com");
        request = post("/api/account/registered");
        Helper.setRequestAllJsonType(request);
        request.content(Helper.jsonMapper.writeValueAsString(checked));
        mockMvc.perform(request)
                .andExpect(jsonPath(Helper.JSON_RESULT_KEY).value(false));
    }

    /**
     * there's no other method, since we cannot get the token generated in service layer
     * @param userId
     */
    private void activate(long userId, String token) {
        userService.activate(userId, token);
    }

    /**
     * Don't call {@link com.fan2fan.service.user.UserService#signup(com.fan2fan.model.User)}
     * since it'll waste mailgun account to send an email to user.
     * @param user
     */
    private UserToken signUp(User user) {
        user.setType(User.USER_TYPE.END_USER);
        user.setCreateTime(TimeUtils.getCurrentTimeAbortMillis());

        // create password, use md5 hashed createTime as salt
        user.setPassword(PasswordUtils.createPassword(
                user.getPassword(), PasswordUtils.getUserSalt(user)));
        user.setId(userDao.insertBasicTables(user));

        return createToken(user);
    }

    private UserToken createToken(User user) {
        String token = SecurityUtils.genToken();
        Timestamp expires = new Timestamp(System.currentTimeMillis() + 300000); // 5 min
        userDao.insertUserToken(user.getId(), token, expires);
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(user.getId());
        userToken.setUsed(false);
        userToken.setExpires(expires);
        return userToken;
    }
}
