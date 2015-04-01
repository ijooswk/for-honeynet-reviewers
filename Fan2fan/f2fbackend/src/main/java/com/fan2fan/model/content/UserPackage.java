package com.fan2fan.model.content;

import com.fan2fan.model.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 * User Package
 * @author huangsz
 */
public class UserPackage extends ContentModel {

    private Integer price;

    private Long currencyId;

    private Currency currency;

    /**
     * the max number of people participating
     */
    private Integer maxPeople;

    /**
     * the min number of people participating
     */
    private Integer minPeople;

    /**
     * the start time of the package activity
     */
    @DateTimeFormat(pattern="MM-dd-yyyy")
    private Date startTime;

    /**
     * the end time of the package activity
     */
    @DateTimeFormat(pattern="MM-dd-yyyy")
    private Date endTime;

    /**
     * the included items, which is the string representation of a List<String></String>
     */
    private String items;

    /**
     * detailed description of the package
     */
    private String desc;

    /**
     * video link provided by Youku. It'll serve the Chinese users.
     * And the global users may prefer to user video provided by Youtube
     */
    private String youkuVideoId;

    private List<PackageTrip> trips;

    private List<String> itemTips;

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<PackageTrip> getTrips() {
        return trips;
    }

    public void setTrips(List<PackageTrip> trips) {
        this.trips = trips;
    }

    public List<String> getItemTips() {
        return itemTips;
    }

    public void setItemTips(List<String> itemTips) {
        this.itemTips = itemTips;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(Integer minPeople) {
        this.minPeople = minPeople;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getYoukuVideoId() {
        return youkuVideoId;
    }

    public void setYoukuVideoId(String youkuVideoId) {
        this.youkuVideoId = youkuVideoId;
    }
}
