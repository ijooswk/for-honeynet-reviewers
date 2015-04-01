package com.fan2fan.service.location.impl;

import com.fan2fan.model.Location;
import com.fan2fan.service.location.LocationService;
import com.fan2fan.dao.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Li YuanChun
 */
@Service
public class LocationServiceImpl implements LocationService {

    private final String DEFAULT_LOCALE = "en";

    /**
     * the currently supported locales
     */
    private Set<String> supportedLocales = new HashSet<>(Arrays.asList("en"));

    @Autowired
    private LocationDao locationDao;

    /**
     * get all countries, using the locale specific language
     * @param locale
     * @return
     */
    @Override
    public List<Location> getAllCountries(String locale){
        return locationDao.getAllCountries(getLocale(locale));
    }

    /**
     * get all cities, using the locale specific language
     * @param locale
     * @return
     */
    @Override
    public List<Location> getAllCities(String locale){
        return locationDao.getAllCities(getLocale(locale));
    }

    /**
     * get cities belonging to the country, using the locale specific language
     * @param locale
     * @param countryName
     * @return
     */
    @Override
    public List<Location> getCitiesByCountryName(String locale, String countryName) {
        locale = getLocale(locale);
        long countryId = locationDao.getCountryIdByName(locale, countryName);
        return locationDao.getCitiesByCountryId(locale, countryId);
    }

    /**
     * get cities belonging to the country by country id, using the locale specific language
     * @param locale
     * @param countryId
     * @return
     */
    @Override
    public List<Location> getCitiesByCountryId(String locale, long countryId){
        return locationDao.getCitiesByCountryId(getLocale(locale), countryId);
    }

    /**
     * get country name by country id, using the locale specific language
     * @param locale
     * @param countryId
     * @return
     */
    @Override
    public Location getCountryById(String locale, long countryId){
        return locationDao.getCountryById(getLocale(locale), countryId);
    };

    /**
     * get country id by name, using the locale specific language
     * @param locale
     * @param countryName
     * @return
     */
    @Override
    public long getCountryIdByName(String locale, String countryName){
        return locationDao.getCountryIdByName(getLocale(locale), countryName);
    };

    private String getLocale(String locale) {
        return supportedLocales.contains(locale) ? locale : DEFAULT_LOCALE;
    }

}
