package com.fan2fan.model.content;

import com.fan2fan.model.BaseModel;

/**
 * Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 * Trip inside a package
 * @author huangsz
 */
public class PackageTrip extends BaseModel {

    /**
     * place of the trip
     */
    private String place;

    /**
     * the summary of the trip
     */
    private String summary;

    /**
     * detailed description
     */
    private String desc;

    /**
     * image url of this package trip
     */
    private String imageUrl;

    /**
     * belonged package Id
     */
    private Long packageId;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
}
