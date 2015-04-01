package com.fan2fan.dao;

import com.fan2fan.model.Team;

import java.util.List;

/**
 * @author huangsz
 */
public interface TeamDao {

    /**
     * insert a new team. create team and teamlocale table
     * @param team
     * @return the newly created team's id
     */
    public long insertTeam(Team team);

    /**
     * update team's table
     * @param team
     */
    public void updateTeam(Team team);

    /**
     * get team from database by id, throws runtime exception if not exists
     * @param teamId
     * @return
     */
    public Team getTeamById(long teamId);

    /**
     * Team registered or not. Two clubs cannot be exactly the same in these
     * conditions: name, city, country, leagueId; and two national teams cannot
     * share the same name and country.
     * @param team
     * @return
     */
    public boolean teamRegistered(Team team);

    /**
     * update team's logoUrl.
     * the ultimate url is: [BASEPATH]/[teamId]/logo/[fileName]
     * @param teamId
     * @param fileName
     */
    public void updateLogoUrl(long teamId, String fileName);

    /**
     * get hot teams
     * @param size
     * @return
     */
    public List<Team> getHotTeams(int size);

    /**
     * get teams by league id
     * @param leagueId id of specific league
     * @return team list
     */
    public List<Team> getTeamsByLeagueId(long leagueId);
}
