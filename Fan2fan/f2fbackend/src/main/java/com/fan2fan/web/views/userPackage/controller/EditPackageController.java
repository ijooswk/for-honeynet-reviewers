package com.fan2fan.web.views.userPackage.controller;

import com.fan2fan.model.User;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.Result;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.intercept.UserTypeAuth;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huangsz
 */
@Controller
@RequestMapping(value = "/packages/{id}")
public class EditPackageController {

    public static final Logger logger = LoggerFactory.getLogger(EditPackageController.class);

    private static final String PAGE_NAME = "pageName";
    private static final String PACKAGE_COMMAND = "packageCommand";
    private static final String EDIT_PACKAGE_VIEW = "package/editPackage";

    @Autowired
    private PackageService packageService;

    @ModelAttribute(PAGE_NAME)
    public String pageName() {
        return "edit";
    }

    @RequestMapping(value = "/edit/", method = RequestMethod.GET)
    @UserTypeAuth({User.USER_TYPE.PARTNER_FAN})
    @LoginRequired
    public String showEditPackagePage(@PathVariable("id") int id, Model model,
                                      HttpServletResponse response) throws IOException {
        UserPackage userPackage = packageService.getPackage(id);
        if (!userPackage.getCreatorId().equals(SessionManager.getUserId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        logger.debug("{}", userPackage.getTrips().get(0).getDesc());
        logger.debug("{}", userPackage.getTrips().get(1).getDesc());
        model.addAttribute(PACKAGE_COMMAND, userPackage);

        return EDIT_PACKAGE_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @UserTypeAuth({User.USER_TYPE.PARTNER_FAN})
    @LoginRequired
    public String updatePackage(@PathVariable("id") int id,
                                @ModelAttribute(PACKAGE_COMMAND) UserPackage userPackage,
                                HttpServletResponse response) throws IOException {
        Result result = packageService.updatePackage(userPackage);
        if (result.equals(Result.PERMISSION_DENIED)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        return "redirect:/packages/{id}/";
    }

}
