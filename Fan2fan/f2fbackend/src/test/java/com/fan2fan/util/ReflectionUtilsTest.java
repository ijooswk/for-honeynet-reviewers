package com.fan2fan.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fan2fan.model.Team;
import com.fan2fan.model.User;
import com.fan2fan.model.content.UserPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author huangsz
 */
@RunWith(JUnit4.class)
public class ReflectionUtilsTest {

    @Test
    public void testCopyPropertiesIgnoreNull() {
        User u1 = new User();
        u1.setId(1L);
        u1.setUserName("u1");

        User u2 = new User();
        u2.setPassword("p2");
        u2.setFullName("full");
        u2.setId(2L);
        u2.setType(User.USER_TYPE.PARTNER_FAN);

        ReflectionUtils.copyPropertiesIgnoreNull(u1, u2);
        assertThat(u2.getPassword(), equalTo("p2"));
        assertThat(u2.getId(), equalTo(1L));
        assertThat(u2.getUserName(), equalTo("u1"));
        assertThat(u2.getFullName(), equalTo("full"));
        assertThat(u2.getType(), equalTo(User.USER_TYPE.PARTNER_FAN));
    }

    @Test
    public void testFillPropertiesWithNull() {
        Team team1 = new Team();
        team1.setType(Team.TEAM_TYPE.CLUB);
        team1.setDesc("team1 desc");
        team1.setName("team1");
        team1.setCreatorId(3L);

        Team team2 = new Team();
        team2.setName("team2");
        team2.setStadium("Camp Nou");

        ReflectionUtils.fillPropertiesWithNull(team1, team2);

        assertThat(team2.getName(), equalTo("team2"));
        assertThat(team2.getType(), equalTo(Team.TEAM_TYPE.CLUB));
        assertThat(team2.getDesc(), equalTo("team1 desc"));
        assertThat(team2.getCreatorId(), equalTo(3L));
        assertThat(team2.getStadium(), equalTo("Camp Nou"));
    }

    @Test
    public void testGetValue() {
        UserPackage pkg = new UserPackage();
        pkg.setPrice(30);
        assertThat(ReflectionUtils.getValue(pkg, "price"), equalTo(30));
        assertThat(ReflectionUtils.getValue(pkg, "id"), equalTo(null));
    }
}
