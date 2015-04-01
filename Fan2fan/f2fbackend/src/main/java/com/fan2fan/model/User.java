package com.fan2fan.model;

import com.fan2fan.web.views.account.validator.EmailExist;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.sql.Timestamp;

/**
 * User Object Model.
 *
 * Model Basic Rules:
 * 1. Use Integer, Boolean, Long instead of int, boolean, long
 * 2. If you're going to use enum, then make an Integer to represent it, but use enum_type in getter and setter
 * 3. Had better not to set any default values here
 *
 * @author huangsz
 */
public class User extends BaseResource {

    public static enum USER_TYPE {
        END_USER, PARTNER_FAN, COUNTRY_MANAGER
    }

    private Long id;

    @NotBlank(message = "email address may not be empty")
    @Email(message = "not a well-formed email address")
    @EmailExist(message = "This email address is already in use")
    private String email;

   @Length(min = 1, max = 50)
   private String fullName;

    @NotBlank
    @Length(min = 6, max = 30)
    private String password;

    @NotBlank
    @Length(min = 1, max = 15)
    private String userName;

    /**
     * the first time user sign up
     */
    private Timestamp createTime;

    private Boolean activated;

    /**
     * 0 for end-user, 1 for pf, 2 for country manager.
     * if directly use enum as type, we may face problems in BeanUtils.copyProperties
     */
    private Integer type;

    /**
     * user level, such as Silver, Gold, Diamond and so on
     */
    private Integer level;

    /**
     * willing to receive the notifications from website or not
     */
    private boolean notifiable = false;

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public USER_TYPE getType() {
        return type == null ? null: USER_TYPE.values()[type];
    }

    public void setType(USER_TYPE type) {
        // this is for spring's @RequestBody's concern, spring calls setXXX
        // no matter the xxx has a value or is null. So if the xxx is null,
        // then xxx.ordinal() will throw an exception
        this.type = (type == null) ? null: type.ordinal();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isNotifiable() {
        return notifiable;
    }

    public void setNotifiable(boolean notifiable) {
        this.notifiable = notifiable;
    }

    public Boolean getActivated() {
        return activated;
    }
}
