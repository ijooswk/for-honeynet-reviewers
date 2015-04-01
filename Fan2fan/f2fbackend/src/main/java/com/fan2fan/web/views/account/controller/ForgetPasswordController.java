package com.fan2fan.web.views.account.controller;

import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account/forgetPassword/")
public class ForgetPasswordController {

    public static final Logger logger = LoggerFactory.getLogger(ForgetPasswordController.class);

    private static final String FORGET_VIEW = "user/forget";
    private static final String FORGET_RESULT = "user/forgetResult";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showPage() {
        return FORGET_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String forgetPassword(@RequestParam("email") String email) {
        Result result = userService.forgetPassword(email);
        if (result.isSuccess()) {
            return  FORGET_RESULT;
        }

        return FORGET_VIEW;
    }
}
