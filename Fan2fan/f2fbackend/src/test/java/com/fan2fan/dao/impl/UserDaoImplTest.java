package com.fan2fan.dao.impl;

import com.fan2fan.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit Test class for {@link com.fan2fan.dao.impl.UserDaoImpl}
 * @author huangsz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class UserDaoImplTest {


    @Test
    @Rollback(true)
    /**
     * test method for {@link com.fan2fan.dao.impl.UserDaoImpl#insertBasicTables(com.fan2fan.model.User)}
     */
    public void testInsertBasicTables() {

    }
}
