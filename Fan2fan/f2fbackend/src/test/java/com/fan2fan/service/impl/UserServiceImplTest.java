package com.fan2fan.service.impl;

import com.fan2fan.dao.UserDao;
import com.fan2fan.model.User;
import com.fan2fan.model.UserDetail;
import com.fan2fan.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 *
 * Created by huangsz on 2014/5/25.
 */
public class UserServiceImplTest extends BaseServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUserName("Michael");
        user.setEmail("xxx@xxx.com");
        user.setNotifiable(false);
        user.setPassword("password");
        user.setType(User.USER_TYPE.END_USER);
    }

    /**
     * test method for {@link com.fan2fan.service.user.impl.UserServiceImpl#getUserDetail(long)}
     */
    @Test
    @Rollback(true)
    public void testGetUserDetail() {
        user.setId(userDao.insertBasicTables(user));
        UserDetail detail = userService.getUserDetail(user.getId());
        assertThat(detail.getUserName(), equalTo("Michael"));
        assertThat(detail.getGender(), equalTo(UserDetail.GENDER.FEMALE));
    }

}
