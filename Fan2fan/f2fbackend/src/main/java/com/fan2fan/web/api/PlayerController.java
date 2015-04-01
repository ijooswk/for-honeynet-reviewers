package com.fan2fan.web.api;

import com.fan2fan.model.Player;
import com.fan2fan.service.team.PlayerService;
import com.fan2fan.web.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangsz
 */
@RestController
@RequestMapping(value = "api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap createPlayer(@RequestBody Player player) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, playerService.createPlayer(player).toString());
        map.put(Message.KEY_OBJECT, player);
        return map;
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET, produces = "application/json")
    public Player getPlayer(@PathVariable long playerId) {
        return playerService.getPlayer(playerId);
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap updatePlayer(@PathVariable long playerId, @RequestBody Player player) {
        ModelMap map = new ModelMap();
        player.setId(playerId);
        map.put(Message.KEY_RESULT, playerService.updatePlayer(player).toString());
        return map;
    }
}
