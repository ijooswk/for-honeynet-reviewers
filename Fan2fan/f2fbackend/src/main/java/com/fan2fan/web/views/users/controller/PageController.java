package com.fan2fan.web.views.users.controller;

import com.fan2fan.model.User;
import com.fan2fan.model.UserDetail;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/users/{id}/")
public class PageController {

    public static final Logger logger = LoggerFactory.getLogger(PageController.class);

    private static final String USER_PROFILE_VIEW = "profile/story_item";
    private static final String USER = "user";

    @Autowired
    private UserService userService;

    @Autowired
    private PackageService packageService;

    @ModelAttribute(USER)
    public User getUserProfile(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showUserPage(@ModelAttribute(USER) User user,
                               @PathVariable("id") long id, Model model) {
        if (SessionManager.getUserId().equals(id)) {
            return "redirect:/home/";
        }
        List<UserPackage> packageList = packageService.getCreatedPackages(user.getId(), 0, 100);
        UserDetail userDetail = userService.getUserDetail(user.getId());
        userDetail.setAvatarUrl(userService.getCompleteAvatarUrl(user.getId(), userDetail.getAvatarUrl()));
        model.addAttribute("packages", packageList);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("user", user);

        return USER_PROFILE_VIEW;
    }
}
