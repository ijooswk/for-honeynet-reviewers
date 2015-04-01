package com.fan2fan.dao;

import com.fan2fan.model.InviteCode;
import com.fan2fan.model.User;

/**
 * @author huangsz
 */
public interface InviteCodeDao {

    /**
     * insert a new invite code
     * @param code: contains code, type and invitorId
     */
    public void insertInviteCode(InviteCode code);

    /**
     * get invitation code object by code
     * @param code
     * @return
     */
    public InviteCode getInviteCode(int code);

    /**
     * check if the code exists
     * @param code
     * @return
     */
    public boolean codeExists(int code);

    /**
     * update, set email
     * @param code
     */
    public void update(InviteCode code);
}
