package com.fan2fan.model;

import com.google.common.base.Strings;

/**
 * the invitation code
 * @author huangsz
 */
public class InviteCode extends BaseModel {

    private Long id;

    /**
     * the invitation code
     */
    private Integer code;

    /**
     * the same with user type
     */
    private Integer type;

    /**
     * email uses this code
     */
    private String email;

    /**
     * the invitor's id
     */
    private Long invitorId;

    /**
     * this invitation code was used or not
     * @return
     */
    public boolean isUsed() {
        return !Strings.isNullOrEmpty(email);
    }

    public User.USER_TYPE getType() {
        return type == null ? null: User.USER_TYPE.values()[type];
    }

    public void setType(User.USER_TYPE type) {
        // this is for spring's @RequestBody's concern, spring calls setXXX
        // no matter the xxx has a value or is null. So if the xxx is null,
        // then xxx.ordinal() will throw an exception
        this.type = (type == null) ? null: type.ordinal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getInvitorId() {
        return invitorId;
    }

    public void setInvitorId(Long invitorId) {
        this.invitorId = invitorId;
    }
}
