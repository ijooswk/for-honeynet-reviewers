package com.fan2fan.service.team.impl;

import com.fan2fan.dao.PlayerDao;
import com.fan2fan.model.Player;
import com.fan2fan.service.Result;
import com.fan2fan.service.team.PlayerService;
import com.fan2fan.service.user.UserService;
import com.fan2fan.util.ReflectionUtils;
import com.fan2fan.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huangsz
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Result createPlayer(Player player) {
        if(!userService.isCountryManager(player.getCreatorId())) {
            return Result.PERMISSION_DENIED;
        }
        player.setCreateTime(TimeUtils.getCurrentTimeAbortMillis());
        player.setId(playerDao.insertPlayer(player));
        return Result.SUCCESS;
    }

    @Override
    public Player getPlayer(long playerId) {
        return playerDao.getPlayerById(playerId);
    }

    @Override
    @Transactional
    public Result updatePlayer(Player player) {
        if (player.getOperatorId() == null ||
                !userService.isCountryManager(player.getOperatorId())) {
            return Result.PERMISSION_DENIED;
        }
        Player original = playerDao.getPlayerById(player.getId());
        ReflectionUtils.fillPropertiesWithNull(original, player);
        playerDao.updatePlayer(player);
        return Result.SUCCESS;
    }
}
