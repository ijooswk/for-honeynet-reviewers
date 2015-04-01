package com.fan2fan.service.team.impl;

import com.fan2fan.dao.TeamDao;
import com.fan2fan.model.Team;
import com.fan2fan.service.Result;
import com.fan2fan.service.repository.BasePath;
import com.fan2fan.service.repository.RepositoryService;
import com.fan2fan.service.team.TeamService;
import com.fan2fan.service.user.UserService;
import com.fan2fan.util.ReflectionUtils;
import com.fan2fan.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * Implementations of TeamService
 * @author huangsz
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryService repositoryService;

    @Value("${S3.link}")
    private String S3Link;

    @Override
    @Transactional
    public Result createTeam(Team team) {
        if(!userService.isCountryManager(team.getCreatorId())) {
            return Result.PERMISSION_DENIED;
        }
        if (teamDao.teamRegistered(team)) {
            return Result.OCCUPIED;
        }
        team.setCreateTime(TimeUtils.getCurrentTimeAbortMillis());
        team.setId(teamDao.insertTeam(team));
        return Result.SUCCESS;
    }

    @Override
    @Transactional
    public Result updateTeam(Team team) {
        // leave it the the interceptor
//        if (team.getOperatorId() == null ||
//                !userService.isCountryManager(team.getOperatorId())) {
//            return Result.PERMISSION_DENIED;
//        }
        Team original = teamDao.getTeamById(team.getId());
        if (basicInfoChange(team, original) && teamDao.teamRegistered(original)) {
            // If you're going to update some basic information of the team,
            // You cannot update the team to be the same with some one exists
            return Result.OCCUPIED;
        }
        ReflectionUtils.fillPropertiesWithNull(original, team);
        teamDao.updateTeam(team);
        return Result.SUCCESS;
    }

    @Override
    public Team getTeam(long teamId) {
        return teamDao.getTeamById(teamId);
    }

    @Override
    @Transactional
    public Result uploadTeamLogo(long teamId, InputStream file, String fileName) {
        repositoryService.putFile(BasePath.TEAMS, getTeamLogoPath(teamId), fileName, file);
        teamDao.updateLogoUrl(teamId, fileName);
        return Result.SUCCESS;
    }

    @Override
    public String getCompleteLogoUrl(long teamId, String logoUrl) {
        return String.format("%s/f2fteams/%s%s", S3Link, getTeamLogoPath(teamId), logoUrl);
    }

    @Override
    public List<Team> getHotTeams(int size) {
        // now the hot teams is only random teams with image
        List<Team> teams = teamDao.getHotTeams(size);
        teams.forEach(t -> t.setLogoUrl(getCompleteLogoUrl(t.getId(), t.getLogoUrl())));
        return teams;
    }

    /**
     * check if this object contains basic team information
     * @param team
     * @param original
     * @return
     */
    private boolean basicInfoChange(Team team, Team original) {
        boolean flag = !original.getCountry().equals(team.getCountry()) ||
               !original.getName().equals(team.getName());
        if (original.getType() == Team.TEAM_TYPE.CLUB) {
            flag = flag || !original.getCity().equals(team.getCity()) ||
                    !original.getLeagueId().equals(team.getLeagueId());
        }
        return flag;
    }

    /**
     * return the team's logo folder
     * @param teamId
     * @return
     */
    private String getTeamLogoPath(long teamId) {
        return teamId + "/logo/";
    }

}
