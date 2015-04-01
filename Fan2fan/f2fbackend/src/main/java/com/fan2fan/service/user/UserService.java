package com.fan2fan.service.user;

import com.fan2fan.form.InvitedUserForm;
import com.fan2fan.model.InviteCode;
import com.fan2fan.model.UserDetail;
import com.fan2fan.service.Result;
import com.fan2fan.model.User;

import java.io.InputStream;

/**
 * Account related or user information related services
 *
 * Created by huangsz on 2014/5/18.
 */
public interface UserService {

    /**
     * signup a user
     * @param user: the user object containing information to signup
     * @return: signup result
     */
    public Result signup(User user);

    /**
     * activate a user using a token
     * @param userId: the userId to be activated
     * @param token: the token specific for the user
     * @return: activate result
     */
    public Result activate(long userId, String token);

    /**
     * signin user
     * @param email user email
     * @param password user password
     * @return success if email and password are matched.
     */
    public Result signin(String email, String password);

    /**
     * Get user object by email
     *
     * @param email email of user
     * @return if find return the user object else return null.
     */
    public User getUserByEmail(String email);

    /**
     * change password.
     *
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public Result changePassword(long userId, String oldPwd, String newPwd);

    /**
     * user forget password, will send a reset password link to the email
     * @param email
     * @return
     */
    public Result forgetPassword(String email);

    /**
     * authenticate the token from user reset password email.
     *
     * @param userId user id
     * @param token token from user email link
     *
     * @return if token is correct
     */
    public boolean authenticatePasswordToken(long userId, String token);

    /**
     * reset user password
     * @param userId
     * @param token
     * @param password
     * @return
     */
    public Result resetPassword(long userId, String token, String password);

    /**
     * get user's detailed information by userId
     * @param userId
     * @return
     */
    public UserDetail getUserDetail(long userId);

    /**
     * update user's detailed information
     * @param detail
     */
    public Result updateDetail(UserDetail detail);

    /**
     * check if a user is country manager or not/
     *
     * @param userId
     * @return
     */
    public boolean isCountryManager(long userId);

    /**
     * email registered or not
     * @param email
     * @return
     */
    public boolean emailRegistered(String email);

    /**
     * invite a new user
     * @param user
     * @return
     */
    public Result inviteUser(User user);

    /**
     * signup invited user. type is according to the code
     * @param userForm
     * @return
     */
    public Result signup(InvitedUserForm userForm);

    /**
     * get user by userId
     * @param userId
     * @return
     */
    public User getUser(long userId);

    /**
     * upload avatar file to repository
     * @param userId
     * @param file
     * @param fileName: the original fileName
     * @return
     */
    public Result uploadAvatar(long userId, InputStream file, String fileName);

    /**
     * the complete avatar url is like:
     * https://s3-ap-northeast-1.amazonaws.com/f2fusers/[userId]/avatar/[avatarUrl]
     *
     * @param userId
     * @param avatarUrl
     * @return
     */
    public String getCompleteAvatarUrl(long userId, String avatarUrl);

    /**
     * check if the invitation code is valid
     * @param code invitation code
     * @return true if code is valid
     */
    public boolean isInvitationCodeValid(String code);

    /**
     * create an invitation code, return null for end user
     * @param invitor
     * @return
     */
    public InviteCode createInviteCode(User invitor);
}
