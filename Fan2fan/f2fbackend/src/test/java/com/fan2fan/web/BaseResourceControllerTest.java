package com.fan2fan.web;

import com.fan2fan.dao.UserDao;
import com.fan2fan.dao.impl.ConnManager;
import com.fan2fan.model.User;
import com.fan2fan.web.BaseControllerTest;
import com.fan2fan.web.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * Controller with user or other resource, it's a base class for real controller test
 * @author huangsz
 */
public class BaseResourceControllerTest extends BaseControllerTest {

    @Autowired
    protected ConnManager cm;

    @Autowired
    protected UserDao userDao;

    /**
     * create a country manager
     * @return
     */
    protected User createCountryManager() {
        User countryManager = new User();
        countryManager.setUserName("Country Manager");
        countryManager.setEmail("countrymanager@f2f.com");
        countryManager.setNotifiable(false);
        countryManager.setPassword("xx");
        countryManager.setCreateTime(new Timestamp(System.currentTimeMillis()));
        countryManager.setType(User.USER_TYPE.COUNTRY_MANAGER);
        countryManager.setId(userDao.insertBasicTables(countryManager));

        // activate and set to be countryManager
        cm.getJdbcTemplate().update("update `user` set activated = 1 where id=" + countryManager.getId());
        return countryManager;
    }

    /**
     * create a common user
     * @return
     */
    protected User createCommonUser() {
        User commonUser = new User();
        commonUser.setUserName("testusername");
        commonUser.setEmail("common@f2f.com");
        commonUser.setNotifiable(false);
        commonUser.setPassword("xx");
        commonUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commonUser.setType(User.USER_TYPE.END_USER);
        commonUser.setId(userDao.insertBasicTables(commonUser));

        // activate and set to be commonUser
        cm.getJdbcTemplate().update("update `user` set activated = 1 where id=" + commonUser.getId());
        return commonUser;
    }

    protected void setUpSession(User user) {
        SessionManager.setUser(user);
    }
}
