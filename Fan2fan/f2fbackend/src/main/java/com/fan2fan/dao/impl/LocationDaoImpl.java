package com.fan2fan.dao.impl;

import com.fan2fan.dao.LocationDao;
import com.fan2fan.dao.impl.mapper.RowMapperFactory;
import com.fan2fan.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuanchun on 14-6-17.
 */
@Repository
public class LocationDaoImpl implements LocationDao{

    @Autowired
    private ConnManager cm;

    @Autowired
    private RowMapperFactory mapperFactory;

    @Override
    public List<Location> getAllCountries(String locale) {
        String nameColumn = "name_" + locale;
        return cm.getJdbcTemplate().query(
                String.format("select `%s` as `name` from `countries`", nameColumn),
                mapperFactory.getLocationRowMapper()
        );
    }

    @Override
    public List<Location> getAllCities(String locale) {
        String nameColumn = "name_" + locale;
        return cm.getJdbcTemplate().query(
                String.format("select `%s` as `name` from `cities`", nameColumn),
                mapperFactory.getLocationRowMapper()
        );
    }

    @Override
    public List<Location> getCitiesByCountryId(String locale, long countryId) {
        String nameColumn = "name_" + locale;
        return cm.getJdbcTemplate().query(
                String.format("select `%s` as `name` from `cities` where `countryId` = ?", nameColumn),
                new Object[]{countryId},
                mapperFactory.getLocationRowMapper()
        );
    }

    @Override
    public Location getCountryById(String locale, long countryId) {
        String nameColumn = "name_" + locale;
        return cm.getJdbcTemplate().queryForObject(
                String.format("select `%s` as `name` from `countries` where `id` = ?", nameColumn),
                new Object[]{countryId},
                mapperFactory.getLocationRowMapper()
        );
    }

    @Override
    public long getCountryIdByName(String locale, String countryName) {
        String nameColumn = "name_" + locale;
        return cm.getJdbcTemplate().queryForObject(
                String.format("select `id` from `countries` where `%s` = ?", nameColumn),
                new Object[]{countryName},
                Long.class
        );
    }
}
