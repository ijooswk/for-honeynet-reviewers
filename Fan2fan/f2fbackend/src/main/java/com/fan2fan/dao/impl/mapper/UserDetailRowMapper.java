package com.fan2fan.dao.impl.mapper;

import com.fan2fan.model.UserDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * map from a result set to a UserDetail object.
 * @author huangsz
 */
public class UserDetailRowMapper implements RowMapper<UserDetail> {

    @Override
    public UserDetail mapRow(ResultSet rs, int i) throws SQLException {
        UserDetail detail = new UserDetail();
        detail.setGender(UserDetail.GENDER.values()[rs.getInt("gender")]);
        detail.setAvatarUrl(rs.getString("avatarUrl"));
        detail.setNation(rs.getString("nation"));
        detail.setCity(rs.getString("city"));
        detail.setProvince(rs.getString("province"));
        detail.setDesc(rs.getString("desc"));
        detail.setPhone(rs.getString("phone"));
        detail.setAddress(rs.getString("address"));
        detail.setOccupation(rs.getString("occupation"));
        detail.setBirthYear(rs.getInt("birthYear"));
        detail.setBirthMonth(rs.getInt("birthMonth"));
        detail.setBirthDay(rs.getInt("birthDay"));
        if (detail.getBirthYear() != 0 && detail.getBirthMonth() !=0 && detail.getBirthDay() != 0) {
            detail.setBirth(LocalDate.of(detail.getBirthYear(), detail.getBirthMonth(), detail.getBirthDay()));
        }
        return detail;
    }

}
