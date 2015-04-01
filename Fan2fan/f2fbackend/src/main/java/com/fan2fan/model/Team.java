package com.fan2fan.model;

import java.sql.Timestamp;

/**
 * Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 * Team object
 *
 * @author huangsz
 */
public class Team extends BaseResource {

    public static enum TEAM_TYPE {
        CLUB, NATIONAL
    }

    private Long id;

    private String name;

    /**
     * description
     */
    private String desc;

    /**
     * the city where the team locates
     */
    private String city;

    /**
     * the country where the team locates
     */
    private String country;

    /**
     * the home stadium
     */
    private String stadium;

    /**
     * team type: 0 for club, 1 for nation.
     * if directly use enum as type, we may face problems in BeanUtils.copyProperties
     */
    private Integer type;

    /**
     * user id for who creates the team
     */
    private Long creatorId;

    private Timestamp createTime;

    /**
     * the id of the team's belonged league
     */
    private Long leagueId;

    /**
     * team logo's url
     */
    private String logoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public void setType(TEAM_TYPE type) {
        /**
         * see comment on {@link User#getType()}
         */
        this.type = type == null ? null : type.ordinal();
    }

    public TEAM_TYPE getType() {
        return type == null ? null: TEAM_TYPE.values()[type];
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
