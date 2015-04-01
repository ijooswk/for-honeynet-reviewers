package com.fan2fan.web.views.index.controller;

import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() {
        if (SessionManager.isLogin()) {
            return "redirect:/home/";
        }
        return "index";
    }

}
