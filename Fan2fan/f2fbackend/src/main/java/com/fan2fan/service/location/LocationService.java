package com.fan2fan.service.location;

import com.fan2fan.model.Location;

import java.util.List;

/**
 * Provide service of locations, such as countries, cities.
 * May provide locations near by xxx function in the future.
 *
 * locale currently is 'en'
 *
 * @author huangsz
 */
public interface LocationService {

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
     * get cities belonging to the country, using the locale specific language
     * @param locale
     * @param countryName
     * @return
     */
    public List<Location> getCitiesByCountryName(String locale, String countryName);

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
