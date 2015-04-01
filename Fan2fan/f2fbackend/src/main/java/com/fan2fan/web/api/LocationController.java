package com.fan2fan.web.api;

import com.fan2fan.model.Location;
import com.fan2fan.service.location.LocationService;
import com.fan2fan.web.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangsz
 */
@RestController
@RequestMapping(value = "api/location/")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "cities/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<Location> getCitiesByCountryName(@RequestBody Location country) {
        return locationService.getCitiesByCountryName(SessionManager.getLocale(), country.getName());
    }
}
