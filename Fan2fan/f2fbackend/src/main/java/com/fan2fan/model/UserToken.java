package com.fan2fan.model;

import java.sql.Timestamp;

/**
 * Note: If we have other tokens, then we can come up with a basic class Token.
 * And UserToken extends from the basic Token class.
 *
 * UserToken means a token for a user to change personal information once.
 * Such as forget password or activate, no need to distinguish them.
 * And it can only be used once.
 *
 * Created by huangsz on 2014/5/19.
 */
public class UserToken extends BaseModel {

    private Long userId;

    private String token;

    /**
     * the token expiration time
     */
    private Timestamp expires;

    /**
     * the token is used or not
     */
    private Boolean used;

    public UserToken() {}

    public UserToken(long id, long userId, String token, Timestamp expires, boolean used) {
        this.setId(id);
        this.userId = userId;
        this.token = token;
        this.expires = expires;
        this.used = used;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }

    public Boolean isUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
