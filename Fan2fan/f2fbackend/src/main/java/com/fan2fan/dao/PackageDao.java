package com.fan2fan.dao;

import com.fan2fan.model.content.*;

import java.util.List;

/**
 * Dao of user package
 *
 * @author huangsz
 */
public interface PackageDao {

    /**
     * insert a new UserPackage, returns its id
     *
     * @param userPackage
     * @return
     */
    public long insertPackage(UserPackage userPackage);

    /**
     * get userPackage's all columns
     *
     * @param packageId
     * @return
     */
    public UserPackage getUserPackageById(long packageId);

    /**
     * delta update of userPackage, only updates those being edited
     *
     * @param userPackage
     */
    public void update(UserPackage userPackage);

    /**
     * Delete package by id
     *
     * @param pkgId package id
     */
    public void delete(long pkgId);

    /**
     * get package infos by example and by limit
     *
     * @param example
     * @return
     */
    public List<UserPackage> getPackagesByExample(final UserPackage example,
                                                  final int offset, final int length);

}
