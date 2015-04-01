package com.fan2fan.web.api;

import com.fan2fan.model.League;
import com.fan2fan.service.team.LeagueService;
import com.fan2fan.web.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangsz
 */
@RestController
@RequestMapping(value = "api/league")
public class LeagueController {

    @Autowired
    private LeagueService leagueService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap createLeague(@RequestBody League league) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, leagueService.createLeague(league).toString());
        map.put(Message.KEY_OBJECT, league);
        return map;
    }

    @RequestMapping(value = "/{leagueId}", method = RequestMethod.GET, produces = "application/json")
    public League getLeague(@PathVariable long leagueId) {
        return leagueService.getLeague(leagueId);
    }

    @RequestMapping(value = "/{leagueId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap updateLeague(@PathVariable long leagueId, @RequestBody League league) {
        ModelMap map = new ModelMap();
        league.setId(leagueId);
        map.put(Message.KEY_RESULT, leagueService.updateLeague(league).toString());
        return map;
    }
}
