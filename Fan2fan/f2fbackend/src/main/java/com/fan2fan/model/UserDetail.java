package com.fan2fan.model;

import com.google.common.collect.Lists;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

/**
 * Detailed Information of a user. Extends from user class
 *
 * Created by huangsz on 2014/5/25.
 */
public class UserDetail extends User {

    public Long getFavoriteTeamId() {
        return favoriteTeamId;
    }

    public void setFavoriteTeamId(Long favoriteTeamId) {
        this.favoriteTeamId = favoriteTeamId;
    }

    public static enum GENDER {
        FEMALE, MALE
    }

    /**
     * 0 for femail, 1 for male.
     * not using enum directly, since it will cause problems in BeanUtils.copyProperties
     */
    private Integer gender = 0;

    private String avatarUrl;

    private String nation;

    private String city;

    private String province;

    private String address;

    private String desc;

    private String phone;

    private String occupation;

    private Integer birthYear;

    private Integer birthMonth;

    private Integer birthDay;

    @DateTimeFormat(pattern="MM-dd-yyyy")
    private LocalDate birth;

    private Long favoriteTeamId;

    private Team favoriteTeam;

    private List<String> languages = Lists.newArrayList();

    public GENDER getGender() {
        return gender == null ? null : GENDER.values()[gender];
    }

    public void setGender(GENDER gender) {
        this.gender = gender == null ? null : gender.ordinal(); // see comment on {@link User#getType()}
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
        if (birth != null) {
            setBirthYear(birth.getYear());
            setBirthMonth(birth.getMonthValue());
            setBirthDay(birth.getDayOfMonth());
        }
    }

    public Team getFavoriteTeam() {
        return favoriteTeam;
    }

    public void setFavoriteTeam(Team favoriteTeam) {
        this.favoriteTeam = favoriteTeam;
    }

    public Integer getAge() {
        return birthYear == null ? null :
                Calendar.getInstance().get(Calendar.YEAR) - birthYear;
    }

    //    public User toUser() {
//        User user = new User();
//        user.setId(this.getId());
//        user.setEmail(this.getEmail());
//        user.setUserName(this.getUserName());
//        user.setFullName(this.getFullName());
//
//        return user;
//    }
//
//    public UserDetail toUserDetail() {
//        UserDetail detail = new UserDetail();
//        detail.setId(this.getId());
//        detail.setAvatarUrl(this.getAvatarUrl());
//        detail.setDesc(this.getDesc());
//        detail.setGender(this.getGender());
//        detail.setBirthYear(this.getBirthYear());
//        detail.setBirthMonth(this.getBirthMonth());
//        detail.setBirthDay(this.getBirthDay());
//        detail.setBirth(this.getBirth());
//        detail.setPhone(this.getPhone());
//        detail.setAddress(this.getAddress());
//        detail.setNation(this.getNation());
//        detail.setCity(this.getCity());
//        detail.setOccupation(this.getOccupation());
//
//        return detail;
//    }
}
