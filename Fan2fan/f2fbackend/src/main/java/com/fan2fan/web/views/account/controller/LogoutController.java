package com.fan2fan.web.views.account.controller;

import com.fan2fan.web.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class LogoutController {

    @RequestMapping(value = "/logout/", method = RequestMethod.GET)
    public String logout() {
        SessionManager.removeUser();

        return "redirect:/";
    }
}
