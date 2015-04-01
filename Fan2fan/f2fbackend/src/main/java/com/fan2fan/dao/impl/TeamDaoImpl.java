package com.fan2fan.dao.impl;

import com.fan2fan.dao.TeamDao;
import com.fan2fan.model.Team;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huangsz
 */
@Repository
public class TeamDaoImpl extends BaseDaoImpl implements TeamDao {

    private static final String TABLE = "team";

    @Override
    public long insertTeam(Team team) {
        List<String> inserted = new ArrayList<>(Arrays.asList("name", "desc", "type",
                "creatorId", "createTime", "country"));
        if (team.getType().equals(Team.TEAM_TYPE.CLUB)) {
            inserted.add("city");
            inserted.add("leagueId");
        }
        String[] fields = new String[inserted.size()];
        inserted.toArray(fields);
        return connManager.insertReturnId(team, fields, TABLE);
    }

    @Override
    public void updateTeam(Team team) {
        team.setOperatorId(null);   // team now should be an 'example', so operatorId should be erased
        connManager.updateExample(TABLE, team, team.getId());
        // can use updateExample instead
//        if (team.getType() == Team.TEAM_TYPE.CLUB) {
//            String[] attrs = new String[]{"name", "desc", "city", "country", "stadium", "leagueId", "logoUrl"};
//            String sql = connManager.createUpdateSql(attrs, TABLE, team.getId());
//            jdbcTemplate().update(sql, team.getName(), team.getDesc(),
//                    team.getCity(), team.getCountry(), team.getStadium(),
//                    team.getLeagueId(), team.getLogoUrl());
//        } else {
//            String[] attrs = new String[]{"name", "desc"};
//            String sql = connManager.createUpdateSql(attrs, TABLE, team.getId());
//            jdbcTemplate().update(sql, team.getName(), team.getDesc());
//        }
    }

    @Override
    public Team getTeamById(long teamId) {
        return jdbcTemplate().queryForObject(
                "select * from team where id=?", new Object[]{teamId},
                rowMapperFactory.getTeamRowMapper()
        );
    }

    @Override
    public boolean teamRegistered(Team team) {
        if (team.getType() == Team.TEAM_TYPE.CLUB) {
            return jdbcTemplate().queryForObject(
                    String.format("select count(*) from %s where name = ? and city = ? and country = ? and leagueId = ? and type = %d",
                            TABLE, Team.TEAM_TYPE.CLUB.ordinal()),
                    new Object[]{team.getName(), team.getCity(), team.getCountry(), team.getLeagueId()},
                    Integer.class
            ) > 0;
        } else {
            return jdbcTemplate().queryForObject(
                    String.format("select count(*) from %s where name = ? and country = ? and type = %d", TABLE, Team.TEAM_TYPE.NATIONAL.ordinal()),
                    new Object[]{team.getName(), team.getCountry()},
                    Integer.class
            ) > 0;
        }
    }

    @Override
    public void updateLogoUrl(long teamId, String fileName) {
        final String sql = String.format("update `%s` set logoUrl = ? where id = ?", TABLE);
        jdbcTemplate().update(sql, fileName, teamId);
    }

    @Override
    public List<Team> getHotTeams(int size) {
        // the hot teams currently is those logo not null
        final String sql = String.format("select * from `%s` where logoUrl <> '' limit 0, %d", TABLE, size);
        return jdbcTemplate().query(sql, rowMapperFactory.getTeamRowMapper());
    }

    @Override
    public List<Team> getTeamsByLeagueId(long leagueId) {
        return jdbcTemplate().query(
                "select * from team where leagueId=?", new Object[]{ leagueId },
                rowMapperFactory.getTeamRowMapper()
        );
    }
}
