package com.fan2fan.service.impl;

import com.fan2fan.model.Location;
import com.fan2fan.service.location.LocationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanchun on 14-6-17.
 *
 */
public class LocationServiceImplTest extends BaseServiceTest {
    @Autowired
    private LocationService locationService;

    @Test
    public void testAll(){
        List<String> allCountries = new ArrayList<>();
        for (Location location : locationService.getAllCountries("en")){
            allCountries.add(location.getName());
        }
        List<String> allCities = new ArrayList<>();
        for (Location location : locationService.getAllCities("en")){
            allCities.add(location.getName());
        }
        List<String> chinaCities = new ArrayList<>();
        for (Location location : locationService.getCitiesByCountryName("en", "China")){
            chinaCities.add(location.getName());
        }

        /*
        System.out.println("----------all countries----------");
        System.out.println(allCountries);
        System.out.println("----------all countries----------");
        System.out.println(allCities);
        System.out.println("----------cities in China----------");
        System.out.println(chinaCities);
        */
        assertThat(allCountries, hasItem("China"));
        assertThat(allCities, hasItem("New York City"));
        assertThat(chinaCities, hasItem("Beijing"));
    }
}
