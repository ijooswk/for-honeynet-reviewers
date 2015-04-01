package com.fan2fan.dao;

import com.fan2fan.model.Player;

/**
 * @author huangsz
 */
public interface PlayerDao {

    /**
     * insert a new player. create player and playerlocale
     * @param player
     * @return
     */
    public long insertPlayer(Player player);

    /**
     * update player's information
     * @param player
     */
    public void updatePlayer(Player player);

    /**
     * get player from database by id,
     * throws runtime exception if not exists
     * @param playerId
     * @return
     */
    public Player getPlayerById(long playerId);

}
