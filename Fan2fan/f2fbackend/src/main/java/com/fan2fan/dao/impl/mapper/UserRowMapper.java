package com.fan2fan.dao.impl.mapper;

import com.fan2fan.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * map from a result set to a user. which can be used in getUserByEmail or getUserById and so on
 * Created by huangsz on 2014/5/19.
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("fullName"));
        user.setUserName(rs.getString("username"));
        user.setType(User.USER_TYPE.values()[rs.getInt("type")]);
        user.setLevel(rs.getInt("level"));
        user.setCreateTime(rs.getTimestamp("createTime"));
        user.setActivated(rs.getBoolean("activated"));
        user.setNotifiable(rs.getBoolean("notifiable"));
        return user;
    }
}
