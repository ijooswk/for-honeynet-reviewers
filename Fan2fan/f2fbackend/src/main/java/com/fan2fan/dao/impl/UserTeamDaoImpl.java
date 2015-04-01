package com.fan2fan.dao.impl;

import com.fan2fan.dao.UserTeamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserTeamDaoImpl implements UserTeamDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void updateUserFavoriteClub(long userId, long teamId) {
        if (getUserFavoriteTeamId(userId) == null) {
            jdbcTemplate.update("insert into user_team(userId,teamId) values(?,?)",
                    userId, teamId);
        } else {
            jdbcTemplate.update("update user_team set teamId=? where userId=?",
                    teamId, userId);
        }
    }

    @Override
    public Long getUserFavoriteTeamId(long userId) {
        try {
            return this.jdbcTemplate.queryForObject(
                    "select teamId from user_team where userId=?",
                    Long.class, userId);
        } catch (DataAccessException e) {
            return null;
        }
    }
}
