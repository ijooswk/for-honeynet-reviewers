package com.fan2fan.web.views.location.controller;

import com.fan2fan.model.Location;
import com.fan2fan.service.location.LocationService;
import com.fan2fan.web.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.ws.rs.Produces;

/**
 *
 * @author huangsz
 */
@Controller
@RequestMapping(value = "/location/")
public class LocationFragmentController {

    @Autowired
    private SpringTemplateEngine emailTemplateEngine; // created by spring boot

    @Autowired
    private LocationService locationService;

    @Produces("text/html")
    @RequestMapping(value = "cities/", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String getCitiesByCountryName(@RequestBody Location country) {
        final Context ctx = new Context();
        ctx.setVariable("cities",
                locationService.getCitiesByCountryName(SessionManager.getLocale(), country.getName()));
        return emailTemplateEngine.process("location/city_options", ctx);
    }
}
