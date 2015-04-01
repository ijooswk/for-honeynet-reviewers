package com.fan2fan.web.views.users.controller;

import com.fan2fan.model.UserDetail;
import com.fan2fan.service.location.LocationService;
import com.fan2fan.service.team.LeagueService;
import com.fan2fan.service.user.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users/edit/")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LeagueService leagueService;

    private static final String USER_COMMAND = "userCommand";

    private static final String AVATAR_URL = "avatarUrl";

    @UserTypeAuth
    @LoginRequired
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String personalInformation(Model model) {
        UserDetail user = userService.getUserDetail(SessionManager.getUserId());
        model.addAttribute(USER_COMMAND, user);

        model.addAttribute("countries", locationService.getAllCountries(SessionManager.getLocale()));
        String nation = user.getNation();
        if (!Strings.isNullOrEmpty(nation)) {
            model.addAttribute("cities",
                    locationService.getCitiesByCountryName(SessionManager.getLocale(), nation));
        }
        String avatarUrl = userService.getCompleteAvatarUrl(SessionManager.getUserId(), user.getAvatarUrl());
        model.addAttribute(AVATAR_URL, avatarUrl);

        model.addAttribute("leagues", leagueService.getAllLeagues());
        return "user/profile";
    }

    @UserTypeAuth
    @LoginRequired
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String updatePersonalInfo(@ModelAttribute(USER_COMMAND) UserDetail user) {
        user.setId(SessionManager.getUserId());
        userService.updateDetail(user);
        return "redirect:/users/edit/";
    }

}
