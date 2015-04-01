package com.fan2fan.service.team;

import com.fan2fan.model.Team;
import com.fan2fan.service.Result;

import java.io.InputStream;
import java.util.List;

/**
 * Team related APIs
 * @author huangsz
 */
public interface TeamService {

    /**
     * create a new team.
     * @param team
     * @return
     */
    public Result createTeam(Team team);

    /**
     * update team's information
     * @param team: contains message going to be updated
     * @return
     */
    public Result updateTeam(Team team);

    /**
     * get team's detailed information
     * @param teamId
     * @return
     */
    public Team getTeam(long teamId);

    /**
     * upload team image
     * @param teamId
     * @param file
     * @param fileName
     * @return
     */
    public Result uploadTeamLogo(long teamId, InputStream file, String fileName);

    /**
     * the complete logo url is like:
     * https://s3-ap-northeast-1.amazonaws.com/f2fteams/[teamId]/logo/[logoUrl]
     * @param logoUrl
     * @return
     */
    public String getCompleteLogoUrl(long teamId, String logoUrl);

    /**
     * @param size
     * @return
     */
    public List<Team> getHotTeams(int size);
}
