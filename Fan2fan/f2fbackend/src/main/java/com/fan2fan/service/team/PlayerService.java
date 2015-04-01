package com.fan2fan.service.team;

import com.fan2fan.model.Player;
import com.fan2fan.service.Result;

/**
 * @author huangsz
 */
public interface PlayerService {

    /**
     * create a player. the id will be set to the player
     * @param player
     * @return
     */
    public Result createPlayer(Player player);

    /**
     * get player by id
     * @param playerId
     * @return
     */
    public Player getPlayer(long playerId);

    /**
     * incrementally update player's informationd
     * @param player
     * @return
     */
    public Result updatePlayer(Player player);
}
