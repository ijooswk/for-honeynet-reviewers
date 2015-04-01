package com.fan2fan.web.views.userPackage.controller;

import com.fan2fan.model.User;
import com.fan2fan.model.content.PackageTrip;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.intercept.UserTypeAuth;
import com.fan2fan.web.session.SessionManager;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huangsz
 */
@Controller
@RequestMapping(value = "/packages/")
public class CreatePackageController {

    public static final Logger logger = LoggerFactory.getLogger(CreatePackageController.class);

    private static final String PAGE_NAME = "pageName";
    private static final String PACKAGE_COMMAND = "packageCommand";
    private static final String CREATE_PACKAGE_VIEW = "package/editPackage";

    @ModelAttribute(PACKAGE_COMMAND)
    public UserPackage getPackage() {
        UserPackage userPackage = new UserPackage();
        userPackage.setItemTips(Lists.newArrayList());
        userPackage.setTrips(Lists.newArrayList());

        return userPackage;
    }

    @ModelAttribute(PAGE_NAME)
    public String pageName() {
        return "new";
    }

    @Autowired
    private PackageService packageService;

    @RequestMapping(value = "/create/", method = RequestMethod.GET)
    @UserTypeAuth({User.USER_TYPE.PARTNER_FAN})
    @LoginRequired
    public String showCreatePackagePage() {
        return CREATE_PACKAGE_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @UserTypeAuth({User.USER_TYPE.PARTNER_FAN})
    @LoginRequired
    public String createPackage(@ModelAttribute(PACKAGE_COMMAND) UserPackage userPackage) {
        logger.debug("items = {}", userPackage.getItemTips());
        for (PackageTrip trip : userPackage.getTrips()) {
            logger.debug("{} {} {}", trip.getPlace(), trip.getSummary(), trip.getImageUrl());
        }

        logger.debug("{} {}", userPackage.getStartTime(), userPackage.getEndTime());
        logger.debug("{}", userPackage.getYoukuVideoId());
        userPackage.setCreatorId(SessionManager.getUserId());
        userPackage.setCreatorName(SessionManager.getUserName());

        // TODO items and trips not implemented yet
        userPackage.setCurrencyId(1L);  // test
        userPackage.setLanguage("English");
        userPackage.setPublished(true);  // all true for now

        packageService.createPackage(userPackage);
        return String.format("redirect:/packages/%d/", userPackage.getId());
    }
}
