package com.fan2fan.service.packages;

import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.Result;

import java.io.InputStream;
import java.util.List;

/**
 * User package
 *
 * @author huangsz
 */
public interface PackageService {

    /**
     * create new package
     * @param pkg
     * @return
     */
    public Result createPackage(UserPackage pkg);

    /**
     * update those not null
     * @param pkg
     * @return
     */
    public Result updatePackage(UserPackage pkg);

    /**
     * delete package
     * @param pkgId package id
     */
    public void deletePackage(long pkgId);

    /**
     * get user package
     * @param pkgId
     * @return
     */
    public UserPackage getPackage(long pkgId);

    /**
     * get some user's created package info list
     * @param creatorId
     * @param offset
     * @param size
     * @return
     */
    public List<UserPackage> getCreatedPackages(long creatorId, int offset, int size);

    /**
     * get the draft package info list of some user
     * @param creatorId
     * @param offset
     * @param size
     * @return
     */
    public List<UserPackage> getDraftedPackages(long creatorId, int offset, int size);

    /**
     * get the published package info list of some user
     * @param creatorId
     * @param offset
     * @param size
     * @return
     */
    public List<UserPackage> getPublishedPackages(long creatorId, int offset, int size);

    /**
     * upload an image to the package(or a packageTrip),
     * they store in the same place, namely f2fusers/[userId]/packages/[fileName]
     * @param userId
     * @param file
     * @param fileName
     * @return
     */
    public Result uploadImage(long userId, InputStream file, String fileName);

    /**
     * the complete logo url is like:
     * https://s3-ap-northeast-1.amazonaws.com/f2fusers/[userId]/packages/[imageUrl]
     * @param imageUrl
     * @param userId
     * @return
     */
    public String getCompleteImageUrl(String imageUrl, long userId);

    /**
     *
     * @param pkgId
     * @return
     */
    public boolean isPackageExist(long pkgId);

}
