package com.fan2fan.dao.impl;

import com.fan2fan.dao.InviteCodeDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.model.InviteCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author huangsz
 */
@Repository
public class InviteCodeDaoImpl implements InviteCodeDao {

    private static final String TABLE = "invitecode";

    @Autowired
    private ConnManager cm;

    @Autowired
    private RowMapperFactory mapperFactory;

    @Override
    public void insertInviteCode(InviteCode code) {
        final String sql = String.format(
                "insert into `%s` (code, type, invitorId) values(?, ?, ?)", TABLE);
        cm.getJdbcTemplate().update(sql, code.getCode(),
                code.getType().ordinal(), code.getInvitorId());
    }

    @Override
    public InviteCode getInviteCode(int code) {
        return cm.getJdbcTemplate().queryForObject(
            String.format("select * from %s where code = ?", TABLE),
                new Object[]{code}, mapperFactory.getInviteCodeRowMapper()
        );
    }

    @Override
    public boolean codeExists(int code) {
        return cm.getJdbcTemplate().queryForObject(
                String.format("select count(*) from %s where `code`= ?", TABLE),
                new Object[]{code},
                Integer.class) > 0;
    }

    @Override
    public void update(InviteCode code) {
        cm.getJdbcTemplate().update(
            String.format("update %s set `email` = ? where code = ?", TABLE),
                code.getEmail(), code.getCode()
        );
    }
}
