package com.fan2fan.service.team.impl;

import com.fan2fan.dao.LeagueDao;
import com.fan2fan.dao.TeamDao;
import com.fan2fan.model.League;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.LeagueService;
import com.fan2fan.service.user.UserService;
import com.fan2fan.util.ReflectionUtils;
import com.fan2fan.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author huangsz
 */
@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private LeagueDao leagueDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Result createLeague(League league) {
        if (!userService.isCountryManager(league.getCreatorId())) {
            return Result.PERMISSION_DENIED;
        }
        if (leagueDao.leagueRegistered(league.getName())) {
            return Result.OCCUPIED;
        }
        league.setCreateTime(TimeUtils.getCurrentTimeAbortMillis());
        league.setId(leagueDao.insertLeague(league));
        return Result.SUCCESS;
    }

    @Override
    public League getLeague(long leagueId) {
        return leagueDao.getLeagueById(leagueId);
    }

    @Override
    @Transactional
    public Result updateLeague(League league) {
        if (league.getOperatorId() == null ||
                !userService.isCountryManager(league.getOperatorId())) {
            return Result.PERMISSION_DENIED;
        }
        League original = leagueDao.getLeagueById(league.getId());
        boolean nameChanged = !original.getName().equals(league.getName());
        if (nameChanged && leagueDao.leagueRegistered(league.getName())) {
            return Result.OCCUPIED;
        }
        ReflectionUtils.fillPropertiesWithNull(original, league);
        leagueDao.updateLeague(league);
        return Result.SUCCESS;
    }

    @Override
    public List<League> getAllLeagues() {
        List<League> leagues = leagueDao.getAll();
        leagues.forEach(league -> league.setTeams(teamDao.getTeamsByLeagueId(league.getId())));

        return leagues;
    }
}
