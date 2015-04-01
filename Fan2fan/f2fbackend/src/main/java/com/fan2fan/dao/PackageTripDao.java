package com.fan2fan.dao;

import com.fan2fan.model.content.PackageTrip;

import java.util.List;

/**
 * @author huangsz
 */
public interface PackageTripDao {

    /**
     * insert trips belong to a package with pkgId
     * @param trips
     * @param pkgId
     */
    public void insertTrips(List<PackageTrip> trips, long pkgId);

    /**
     * get all trips of a package
     * @param pkgId
     * @return
     */
    public List<PackageTrip> getTrips(long pkgId);

    /**
     * Delete trips of package
     *
     * @param pkgId package id
     */
    public void deleteTripsByPackageId(long pkgId);
}
