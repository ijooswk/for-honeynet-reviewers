package com.fan2fan.dao.impl;

import com.fan2fan.dao.PackageDao;
import com.fan2fan.model.content.UserPackage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangsz
 */
@Repository
public class PackageDaoImpl extends BaseDaoImpl implements PackageDao {

    private static final String TABLE = "package";

    private final String[] inserted =
        new String[]{"title", "creatorId", "creatorName",
            "summary", "language", "published", "views", "ups", "downs", "startTime", "endTime",
            "currencyId", "price", "minPeople", "maxPeople", "desc", "items", "youkuVideoId"};

    @Override
    public long insertPackage(UserPackage userPackage) {
        return connManager.insertReturnId(userPackage, inserted, TABLE);
    }

    @Override
    public UserPackage getUserPackageById(long packageId) {
        return jdbcTemplate().queryForObject(
                String.format("select * from %s where id = ?", TABLE),
                new Object[]{ packageId },
                rowMapperFactory.getPackageRowMapper()
        );
    }

    @Override
    public void update(UserPackage userPackage) {
        jdbcTemplate().update(
                String.format("update %s set title=? where id=?", TABLE),
                userPackage.getTitle(), userPackage.getId());
    }

    @Override
    public void delete(long pkgId) {
        this.jdbcTemplate().update(String.format("delete from %s where id=?", TABLE),pkgId);
    }

    @Override
    public List<UserPackage> getPackagesByExample(UserPackage example, int offset, int length) {
        return connManager.selectByExample(TABLE, example, true,
                rowMapperFactory.getPackageRowMapper(), offset, length);
    }
}
