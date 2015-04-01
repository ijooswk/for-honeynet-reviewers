package com.fan2fan.dao.impl;

import com.fan2fan.dao.LeagueDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.model.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangsz
 */
@Repository
public class LeagueDaoImpl implements LeagueDao {

    private static final String TABLE = "league";

    @Autowired
    private ConnManager cm;

    @Autowired
    private RowMapperFactory mapperFactory;

    private final String[] inserted = new String[]{"name", "desc", "logoUrl", "creatorId", "createTime"};

    @Override
    public long insertLeague(League league) {
        return cm.insertReturnId(league, inserted, TABLE);
    }

    @Override
    public void updateLeague(League league) {
        String[] attrs = new String[]{"name", "desc", "logoUrl"};
        cm.getJdbcTemplate().update(cm.createUpdateSql(attrs, TABLE, league.getId()),
                league.getName(), league.getDesc(), league.getLogoUrl());
    }

    @Override
    public League getLeagueById(long leagueId) {
        return cm.getJdbcTemplate().queryForObject(
                String.format("select * from league where id=?"),
                new Object[]{leagueId},
                mapperFactory.getLeagueRowMapper()
        );
    }

    @Override
    public boolean leagueRegistered(String name) {
        return cm.getJdbcTemplate().queryForObject(
                String.format("select count(*) from `league` where `name`=?"),
                new Object[]{name},
                Integer.class
        ) > 0;
    }

    @Override
    public List<League> getAll() {
        return cm.getJdbcTemplate().query(
                String.format("select * from %s", TABLE),
                mapperFactory.getLeagueRowMapper()
        );
    }
}
