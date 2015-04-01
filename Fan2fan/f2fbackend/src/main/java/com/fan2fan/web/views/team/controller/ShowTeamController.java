package com.fan2fan.web.views.team.controller;

import com.fan2fan.model.League;
import com.fan2fan.model.Team;
import com.fan2fan.model.User;
import com.fan2fan.service.Result;
import com.fan2fan.service.location.LocationService;
import com.fan2fan.service.repository.RepositoryService;
import com.fan2fan.service.team.LeagueService;
import com.fan2fan.service.team.TeamService;
import com.fan2fan.web.Message;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.intercept.UserTypeAuth;
import com.fan2fan.web.session.SessionManager;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Team page controller
 *
 * @author huangsz
 */
@Controller
@RequestMapping("/teams/{teamId}/")
public class ShowTeamController {

    private static final String LOGO_URL = "logoUrl";
    private static final String TEAM_COMMAND = "teamCommand";

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showTeamPage(@PathVariable long teamId, Model model) {
        Team team = teamService.getTeam(teamId);
        model.addAttribute(TEAM_COMMAND, team);
        model.addAttribute(LOGO_URL, teamService.getCompleteLogoUrl(teamId, team.getLogoUrl()));
        return "club/club";
    }
}
