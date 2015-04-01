package com.fan2fan.service.team;

import com.fan2fan.model.League;
import com.fan2fan.service.Result;

import java.util.List;

/**
 * @author huangsz
 */
public interface LeagueService {

    /**
     * create a league
     * @param league
     * @return
     */
    public Result createLeague(League league);

    /**
     * get league by its id
     * @param leagueId
     * @return
     */
    public League getLeague(long leagueId);

    /**
     * Incrementally update a league
     * @param league
     * @return
     */
    public Result updateLeague(League league);

    /**
     * get all leagues
     * @return
     */
    public List<League> getAllLeagues();
}
