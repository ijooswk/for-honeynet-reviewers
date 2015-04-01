package com.fan2fan.dao;

import com.fan2fan.model.Location;

import java.util.List;

/**
 * @author yuanchun
 */
public interface LocationDao {
    /**
     * get all countries, using the locale specific language
     * @param locale
     * @return
     */
    public List<Location> getAllCountries(String locale);

    /**
     * get all cities, using the locale specific language
     * @param locale
     * @return
     */
    public List<Location> getAllCities(String locale);

    /**
     * get cities belonging to the country by country id, using the locale specific language
     * @param locale
     * @param countryId
     * @return
     */
    public List<Location> getCitiesByCountryId(String locale, long countryId);

    /**
     * get country name by country id, using the locale specific language
     * @param locale
     * @param countryId
     * @return
     */
    public Location getCountryById(String locale, long countryId);

    /**
     * get country id by name, using the locale specific language
     * @param locale
     * @param countryName
     * @return
     */
    public long getCountryIdByName(String locale, String countryName);
}
