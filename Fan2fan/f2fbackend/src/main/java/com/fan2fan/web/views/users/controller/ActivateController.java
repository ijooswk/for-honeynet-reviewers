package com.fan2fan.web.views.users.controller;

import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class ActivateController {

    public static final Logger logger = LoggerFactory.getLogger(ActivateController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}/activate/{token}", method = RequestMethod.GET)
    public String activate(@PathVariable long userId, @PathVariable String token) {
        Result result = userService.activate(userId, token);
        logger.debug("activate result is {}", result);

        return "redirect:/account/sign/";
    }
}
