package com.fan2fan.web.api;

import com.fan2fan.model.Team;
import com.fan2fan.service.team.TeamService;
import com.fan2fan.web.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for team related APIs
 *
 * @author huangsz
 */
@RestController
@RequestMapping(value = "api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap createTeam(@RequestBody Team team) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, teamService.createTeam(team).toString());
        map.put(Message.KEY_OBJECT, team);
        return map;
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET, produces = "application/json")
    public Team getTeam(@PathVariable long teamId) {
        return teamService.getTeam(teamId);
    }

    @RequestMapping(value = "/{teamId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap updateTeam(@PathVariable long teamId, @RequestBody Team team) {
        ModelMap map = new ModelMap();
        team.setId(teamId);
        map.put(Message.KEY_RESULT, teamService.updateTeam(team).toString());
        return map;
    }
}
