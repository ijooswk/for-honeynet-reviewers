package com.fan2fan.web.views.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class CustomErrorController {

    @RequestMapping("/403.html")
    public String forbidden() {
        return "errors/403";
    }

    @RequestMapping("/404.html")
    public String pageNotFound() {
        return "errors/404";
    }

    @RequestMapping("/500.html")
    public String internalServerError() {
        return "errors/500";
    }
}
