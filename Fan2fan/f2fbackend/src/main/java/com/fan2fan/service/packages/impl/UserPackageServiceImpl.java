package com.fan2fan.service.packages.impl;

import com.fan2fan.dao.PackageDao;
import com.fan2fan.dao.PackageTripDao;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.Result;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.service.repository.BasePath;
import com.fan2fan.service.repository.RepositoryService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * @author huangsz
 */
@Service
public class UserPackageServiceImpl implements PackageService {

    public static final Logger logger = LoggerFactory.getLogger(UserPackageServiceImpl.class);

    @Autowired
    private PackageDao packageDao;

    @Autowired
    private PackageTripDao packageTripDao;

    @Autowired
    private RepositoryService repositoryService;

    @Value("${S3.link}")
    private String S3Link;

    private static final String ITEM_SEPARATOR = "##;##";

    @Override
    @Transactional
    public Result createPackage(UserPackage pkg) {
        deflatePackage(pkg);
        pkg.setId(packageDao.insertPackage(pkg));
        if (pkg.getTrips() != null) {
            packageTripDao.insertTrips(pkg.getTrips(), pkg.getId());
        }
        return Result.SUCCESS;
    }

    @Override
    @Transactional
    public Result updatePackage(UserPackage pkg) {
        deflatePackage(pkg);
        UserPackage orig = packageDao.getUserPackageById(pkg.getId());
        if (orig.getCreatorId().equals(pkg.getOperatorId())) {
            return Result.PERMISSION_DENIED;
        }
        packageDao.update(pkg);
        packageTripDao.deleteTripsByPackageId(pkg.getId());
        pkg.getTrips().forEach(trip -> logger.debug("trip {} = {}", trip.getPlace(), trip.getDesc()));
        packageTripDao.insertTrips(pkg.getTrips(), pkg.getId());

        return Result.SUCCESS;
    }

    @Override
    public void deletePackage(long pkgId) {
        if (getPackage(pkgId) == null) {
            return;
        }

        packageTripDao.deleteTripsByPackageId(pkgId);
        packageDao.delete(pkgId);
    }

    @Override
    public UserPackage getPackage(long pkgId) {
        return inflatePackage(packageDao.getUserPackageById(pkgId));
    }

    @Override
    public List<UserPackage> getCreatedPackages(long creatorId, int offset, int size) {
        UserPackage example = new UserPackage();
        example.setCreatorId(creatorId);
        List<UserPackage> pkgs = packageDao.getPackagesByExample(example, offset, size);
        pkgs.forEach(this::inflatePackage);
        return pkgs;
    }

    @Override
    public List<UserPackage> getDraftedPackages(long creatorId, int offset, int size) {
        UserPackage example = new UserPackage();
        example.setCreatorId(creatorId);
        example.setPublished(false);
        List<UserPackage> pkgs = packageDao.getPackagesByExample(example, offset, size);
        pkgs.forEach(this::inflatePackage);
        return pkgs;
    }

    @Override
    public List<UserPackage> getPublishedPackages(long creatorId, int offset, int size) {
        UserPackage example = new UserPackage();
        example.setCreatorId(creatorId);
        example.setPublished(true);
        List<UserPackage> pkgs = packageDao.getPackagesByExample(example, offset, size);
        pkgs.forEach(this::inflatePackage);
        return pkgs;
    }

    @Override
    public Result uploadImage(long userId, InputStream file, String fileName) {
        repositoryService.putFile(BasePath.USERS, getPackageFilePath(userId), fileName, file);
        return Result.SUCCESS;
    }

    @Override
    public String getCompleteImageUrl(String imageUrl, long userId) {
        return repositoryService.getPath(BasePath.USERS, getPackageFilePath(userId), imageUrl);
    }

    @Override
    public boolean isPackageExist(long pkgId) {
        UserPackage example = new UserPackage();
        example.setId(pkgId);
        return !packageDao.getPackagesByExample(example,0, 1).isEmpty();
    }

    /**
     * make List<String> itemTips to String items
     * @param pkg
     * @return
     */
    private UserPackage deflatePackage(UserPackage pkg) {
        if(pkg.getItemTips() != null) {
            pkg.setItems(String.join(ITEM_SEPARATOR, pkg.getItemTips()));
        } else {
            pkg.setItems("");
        }
        return pkg;
    }

    /**
     * make String items to List<String> itemTips.
     * load trips of package
     * @param pkg
     * @return
     */
    private UserPackage inflatePackage(UserPackage pkg) {
        if (!Strings.isNullOrEmpty(pkg.getItems())) {
            pkg.setItemTips(Lists.newArrayList(pkg.getItems().split(ITEM_SEPARATOR)));
        }
        pkg.setTrips(packageTripDao.getTrips(pkg.getId()));
        pkg.getTrips().forEach(trip -> trip.setImageUrl(getCompleteImageUrl(trip.getImageUrl(), pkg.getCreatorId())));
        return pkg;
    }

    /**
     * user package file's repository path
     * @param userId
     * @return
     */
    private String getPackageFilePath(long userId) {
        return String.format("%d/packages", userId);
    }
}
