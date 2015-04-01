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
@RequestMapping("/teams/{teamId}")
public class EditTeamController {

    private static final Logger logger = LoggerFactory.getLogger(EditTeamController.class);

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

    @ModelAttribute(PAGE_NAME)
    public String pageName() {
        return "edit";
    }

    @ModelAttribute(LEAGUE_SELECTIONS)
    public List<League> leagueSelections(){ return leagueService.getAllLeagues(); }

    @LoginRequired
    @UserTypeAuth(User.USER_TYPE.PARTNER_FAN)
    @RequestMapping(value = "/edit/", method = RequestMethod.GET)
    public String teamEditPage(Model model, @PathVariable int teamId) {
        Team team = teamService.getTeam(teamId);
        model.addAttribute(TEAM_COMMAND, team);
        model.addAttribute(NAV, "edit");
        model.addAttribute(LOGO_URL, teamService.getCompleteLogoUrl(teamId, team.getLogoUrl()));
        model.addAttribute("countries", locationService.getAllCountries(SessionManager.getLocale()));
        if (!Strings.isNullOrEmpty(team.getCountry())) {
            model.addAttribute("cities",
                    locationService.getCitiesByCountryName(SessionManager.getLocale(), team.getCountry()));
        }
        return "club/club_edit";
    }

    @LoginRequired
    @UserTypeAuth(User.USER_TYPE.PARTNER_FAN)
    @RequestMapping(value = "/edit/", method = RequestMethod.POST)
    public String editTeam(@PathVariable("teamId") int teamId,
                           final RedirectAttributes redirectAttributes) {
        Team team = teamService.getTeam(teamId);
        Result result = teamService.updateTeam(team);
        redirectAttributes.addFlashAttribute(Message.KEY_MESSAGE, result.toString());
        return "redirect:/teams/{teamId}/";
    }

    @LoginRequired
    @UserTypeAuth({User.USER_TYPE.COUNTRY_MANAGER, User.USER_TYPE.PARTNER_FAN})
    @RequestMapping(value = "/players/", method = RequestMethod.GET)
    public String teamPlayersEditPage(@PathVariable long teamId, Model model) {
        model.addAttribute(TEAM_COMMAND, teamService.getTeam(teamId));
        model.addAttribute(NAV, "players");
        return "club/club_players";
    }

}
