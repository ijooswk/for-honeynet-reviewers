package com.fan2fan.dao.impl;

import com.fan2fan.dao.PackageTripDao;
import com.fan2fan.model.content.PackageTrip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangsz
 */
@Repository
public class PackageTripDaoImpl extends BaseDaoImpl implements PackageTripDao {

    public static final Logger logger = LoggerFactory.getLogger(PackageTripDaoImpl.class);

    private static final String TABLE = "packageTrip";

    private String[] inserted = new String[]{"`place`", "`summary`", "`desc`", "`imageUrl`", "`packageId`"};

    @Override
    public void insertTrips(List<PackageTrip> trips, long pkgId) {
        if (trips == null || trips.size() <= 0) {
            return;
        }
        List<Object[]> objects = trips.stream().map(t -> new Object[]{
                t.getPlace(), t.getSummary() == null ? "" : t.getSummary(),
                t.getDesc(), t.getImageUrl() == null ? "" : t.getImageUrl(), pkgId
        }).collect(Collectors.toList());
        objects.forEach(o -> logger.debug("{}!{}!{}", o, o.length, o[2]));
        final String sql = String.format("insert into %s(%s) values(?,?,?,?,?)", TABLE, String.join(",", inserted));
        jdbcTemplate().batchUpdate(sql, objects);
    }

    @Override
    public List<PackageTrip> getTrips(long pkgId) {
        PackageTrip example = new PackageTrip();
        example.setPackageId(pkgId);
        return connManager.selectByExample(TABLE, example, true, rowMapperFactory.getPackageTripRowMapper());
    }

    @Override
    public void deleteTripsByPackageId(long pkgId) {
        String sql = String.format("delete from %s where packageId=?", TABLE);
        jdbcTemplate().update(sql, pkgId);
    }
}
