package com.fan2fan.dao;

import com.fan2fan.model.League;

import java.util.List;

/**
 * @author huangsz
 */
public interface LeagueDao {

    /**
     * insert a new league, create league and leaguelocale table
     * @param league
     * @return
     */
    public long insertLeague(League league);

    /**
     * update league's table
     * @param league
     */
    public void updateLeague(League league);

    /**
     * get league from database by id
     * @param leagueId
     * @return
     */
    public League getLeagueById(long leagueId);

    /**
     * league league registered or not. The constrant is only name
     * @param name
     * @return
     */
    public boolean leagueRegistered(String name);

    /**
     * get all leagues
     * @return
     */
    public List<League> getAll();
}
