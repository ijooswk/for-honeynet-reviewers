package com.fan2fan.web.views.index.controller;

import com.fan2fan.model.UserDetail;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/home/")
public class HomeController {

    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String HOME_VIEW = "profile/story_item";
    private static final String USER = "user";

    @Autowired
    private UserService userService;

    @Autowired
    private PackageService packageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @LoginRequired
    public String home(Model model) {
        long uid = SessionManager.getUserId();
        model.addAttribute(USER, userService.getUser(uid));
        UserDetail userDetail = userService.getUserDetail(uid);
        userDetail.setAvatarUrl(userService.getCompleteAvatarUrl(uid, userDetail.getAvatarUrl()));
        List<UserPackage> packageList = packageService.getCreatedPackages(uid, 0, 100);
        model.addAttribute("packages", packageList);
        model.addAttribute("userDetail", userDetail);
        model.addAttribute("isHome", true);

        return HOME_VIEW;
    }
}
