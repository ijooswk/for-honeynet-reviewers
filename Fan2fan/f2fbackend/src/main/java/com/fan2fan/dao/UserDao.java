package com.fan2fan.dao;

import com.fan2fan.model.User;
import com.fan2fan.model.UserDetail;
import com.fan2fan.model.UserRecord;
import com.fan2fan.model.UserToken;

import java.sql.Timestamp;

/**
 * user dao
 * Created by huangsz on 2014/5/18.
 */
public interface UserDao {

    /**
     * insert table `user`, `user_details` and `user_record`
     * @param user
     * @return: the newly inserted user's id, the three should be the same
     */
    public long insertBasicTables(User user);

    /**
     * insert user_token table
     * @param userId, the user to be activated
     * @param token, activation token
     * @param expires, expiration time
     */
    public void insertUserToken(long userId, String token, Timestamp expires);

    /**
     * check the email is registered or not
     * @param email
     * @return: true for the email is registered
     */
    public boolean emailRegistered(String email);

    /**
     * check if the userName has been taken or not
     * @param userName
     * @return
     */
    public boolean userNameRegistered(String userName);

    /**
     * get the user token from user_token table, throws EmptyResultDataAccessException if there's no data
     * @param token
     * @param userId
     * @return: the UserToken object
     */
    public UserToken getUserToken(String token, long userId);

    /**
     * activate a user
     * @param userToken
     */
    public void activateUser(UserToken userToken);

    /**
     * get user from email, return null if no use.
     * Throw DataAccessException if more than one user
     * @param email
     * @return
     */
    public User getUserByEmail(String email);

    /**
     * get the user object by user's id, throws EmptyResultDataAccessException if there's no data
     * @param id
     * @return
     */
    public User getUserById(long id);

    /**
     * update password of user
     * @param id userId
     * @param newPwd the new password
     */
    public void updatePassword(long id, String newPwd);

    /**
     * mark the UserToken as used
     * @param tokenId
     */
    public void markTokenUsed(long tokenId);

    /**
     * get a instance from user_detail table
     * @param userId
     * @return
     */
    public UserDetail getUserDetailById(long userId);

    /**
     * Incremental update user table
     * @param user
     */
    public void updateUser(User user);

    /**
     * Incremental update user_detail table
     * @param detail
     */
    public void updateUserDetail(UserDetail detail);

    /**
     * get user's password from database
     * @param userId
     * @return
     */
    public String getPassword(long userId);

    /**
     * change points in userDetail
     * @param userId
     * @param points: the points to add or subtract
     */
    public void changePoints(long userId, int points);

    /**
     * get UserRecord by user id
     * @param userId
     * @return
     */
    public UserRecord getUserRecordById(long userId);

    /**
     * update user record
     * @param record
     */
    public void updateUserRecord(UserRecord record);

    /**
     * update user's avatarUrl. the ultimate url is: [BASEPATH]/[userId]/avatar/[fileName]
     * @param userId
     * @param fileName: the fileName under avatar folder
     */
    public void updateAvatarUrl(long userId, String fileName);
}
