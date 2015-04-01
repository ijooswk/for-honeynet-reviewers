package com.fan2fan.service;

/**
 * Service handle result.
 * I think it's better than Status Code or Exceptions @huangsz
 *
 * Created by huangsz on 2014/5/18.
 */
public enum Result {
    SUCCESS("success"),
    FAIL("fail"),
    PERMISSION_DENIED("permission_denied"),  // without permission
    VALIDATION_ERROR("validate_error"),  // wrong validation in some fields
    OCCUPIED("occupied"),  // being occupied, such as email
    NOTEXISTS("notexists"),  // not exists, such as the resource you're requested not exist(404)
    WRONG_RES("wrong_res"),  // the wrong resource you're visiting
    OUTDATE("outdate"),  // outdated resource or behavior
    NO_CHANGE("no_change"),  // Nothing changes. Means no operation should be applied
    EXCEPTION("exception");

    private String msg;

    private Result (String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }

    @Override
    public String toString() {
        return msg;
    }
}
