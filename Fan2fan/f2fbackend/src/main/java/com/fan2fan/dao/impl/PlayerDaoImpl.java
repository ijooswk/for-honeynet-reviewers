package com.fan2fan.dao.impl;

import com.fan2fan.dao.PlayerDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author huangsz
 */
@Repository
public class PlayerDaoImpl implements PlayerDao {

    private static final String TABLE = "player";

    @Autowired
    private ConnManager cm;

    @Autowired
    private  RowMapperFactory mapperFactory;

    private final String[] inserted = new String[] {"name", "desc", "country", "number", "position",
            "imageUrl", "teamId", "creatorId", "createTime"};

    private final String[] updated = new String[] {"name", "desc", "country", "number", "position",
            "imageUrl", "teamId"};

    @Override
    public long insertPlayer(Player player) {
        return cm.insertReturnId(player, inserted, TABLE);
    }

    @Override
    public void updatePlayer(Player player) {

        cm.getJdbcTemplate().update(cm.createUpdateSql(updated, TABLE, player.getId()),
                player.getName(), player.getDesc(), player.getCountry(),
                player.getNumber(), player.getPosition(), player.getImageUrl(), player.getTeamId());
    }

    @Override
    public Player getPlayerById(long playerId) {
        return cm.getJdbcTemplate().queryForObject(
                "select * from player where id=?",
                new Object[]{playerId},
                mapperFactory.getPlayerRowMapper()
        );
    }
}
