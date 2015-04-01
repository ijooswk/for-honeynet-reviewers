package com.fan2fan.dao.impl;

import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * the basic class of all dao implementations
 */
public abstract class BaseDaoImpl {

    @Autowired
    protected RowMapperFactory rowMapperFactory;

    @Autowired
    protected ConnManager connManager;

    public JdbcTemplate jdbcTemplate() {
        return connManager.getJdbcTemplate();
    }
}
