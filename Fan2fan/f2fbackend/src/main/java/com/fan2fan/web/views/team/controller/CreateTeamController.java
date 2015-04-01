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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Team page controller
 *
 * @author huangsz
 */
@Controller
@RequestMapping("/teams")
public class CreateTeamController {

    private static final Logger logger = LoggerFactory.getLogger(CreateTeamController.class);

    private static final String TEAM_COMMAND = "teamCommand";

    private static final String PAGE_NAME = "pageName";

    /**
     * navigation of team pages
     */
    private static final String NAV = "nav";

    private static final String LEAGUE_SELECTIONS = "leagues";

    private static final String UPLOAD_MESSAGE = "uploadMessage";

    private static final String LOGO_URL = "logoUrl";

    @Autowired
    private TeamService teamService;

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RepositoryService repositoryService;

    @ModelAttribute(TEAM_COMMAND)
    public Team teamCommand() { return new Team(); }

    @ModelAttribute(PAGE_NAME)
    public String pageName() {
        return "new";
    }

    @ModelAttribute(LEAGUE_SELECTIONS)
    public List<League> leagueSelections(){ return leagueService.getAllLeagues(); }

    @LoginRequired
    @UserTypeAuth(User.USER_TYPE.PARTNER_FAN)
    @RequestMapping(value = "/create/", method = RequestMethod.GET)
    public String createTeamPage(Model model) {
        model.addAttribute("countries", locationService.getAllCountries(SessionManager.getLocale()));
        return "club/club_edit";
    }

    @LoginRequired
    @UserTypeAuth(User.USER_TYPE.PARTNER_FAN)
    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public String createTeam(@ModelAttribute(TEAM_COMMAND) Team team, final RedirectAttributes redirectAttributes) {
        team.setType(Team.TEAM_TYPE.CLUB);
        team.setCreatorId(SessionManager.getUserId());
        Result result = teamService.createTeam(team);
        if (!result.isSuccess()) {
            redirectAttributes.addFlashAttribute(TEAM_COMMAND, team);
            return "redirect:/teams/create/";
        }
        logger.info(String.format("Team: %s created by user[id:%d]", team.getName(), team.getCreatorId()));
        return String.format("redirect:/teams/%d/", team.getId());
    }
}
