package com.fan2fan.service.user.impl;

import com.fan2fan.dao.InviteCodeDao;
import com.fan2fan.dao.TeamDao;
import com.fan2fan.dao.UserDao;
import com.fan2fan.dao.UserTeamDao;
import com.fan2fan.form.InvitedUserForm;
import com.fan2fan.model.*;
import com.fan2fan.service.Result;
import com.fan2fan.service.email.EmailService;
import com.fan2fan.service.repository.BasePath;
import com.fan2fan.service.repository.RepositoryService;
import com.fan2fan.service.reward.RewardService;
import com.fan2fan.service.reward.Trigger;
import com.fan2fan.service.user.UserService;
import com.fan2fan.util.*;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.UrlEscapers;
import com.google.common.primitives.Ints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of UserService
 * Created by huangsz on 2014/5/18.
 */
@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";
    private static final String DEFAULT_AVATAR_URL = "http://partnerfan.com/static/img/default-gravatar.png";

    @Autowired
    private UserDao userDao;

    @Autowired
    private InviteCodeDao inviteCodeDao;

    @Autowired
    private UserTeamDao userTeamDao;

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private SpringTemplateEngine emailTemplateEngine; // created by spring boot

    @Value("${website.link}")
    private String websiteLink;


    /**
     * one day's mill seconds, used in token expires
     */
    private static final long ONE_DAY_MILLS = 86400000;

    @Override
    @Transactional
    public Result signup(User user) {
        if (userDao.emailRegistered(user.getEmail())
                || userDao.userNameRegistered(user.getUserName())) {
            return Result.OCCUPIED;
        }
        user.setType(User.USER_TYPE.END_USER);
        createNewUser(user);
        return Result.SUCCESS;
    }

    @Override
    @Transactional
    public Result activate(long userId, String token) {
        UserToken userToken = userDao.getUserToken(token, userId);
        if (userToken.isUsed() || tokenOutdate(userToken)) {
            return Result.OUTDATE;
        }
        userDao.activateUser(userToken);
        return Result.SUCCESS;
    }

    @Override
    public Result signin(String email, String password) {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            return Result.NOTEXISTS;
        }
        if (!PasswordUtils.authenticatePassword(password,
                PasswordUtils.getUserSalt(user), userDao.getPassword(user.getId()))) {
            return Result.FAIL;
        }
        return Result.SUCCESS;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public Result changePassword(long userId, String oldPwd, String newPwd) {
        User user = userDao.getUserById(userId);
        if (!PasswordUtils.authenticatePassword(oldPwd,
                PasswordUtils.getUserSalt(user), userDao.getPassword(userId))) {
            return Result.FAIL;
        }
        user.setPassword(PasswordUtils.createPassword(newPwd, PasswordUtils.getUserSalt(user)));
        userDao.updatePassword(userId, user.getPassword());
        return Result.SUCCESS;
    }

    @Override
    @Transactional
    public Result forgetPassword(String email) {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            return Result.NOTEXISTS;
        }
        String token = createToken(user.getId());
        sendResetPwdEmail(user, token);
        return Result.SUCCESS;
    }

    @Override
    public boolean authenticatePasswordToken(long userId, String token) {
        UserToken userToken = userDao.getUserToken(token, userId);

        return !userToken.isUsed() && !tokenOutdate(userToken);
    }

    @Override
    @Transactional
    public Result resetPassword(long userId, String token, String password) {
        UserToken userToken = userDao.getUserToken(token, userId);
        if (userToken.isUsed() || tokenOutdate(userToken)) {
            return Result.OUTDATE;
        }
        User user = userDao.getUserById(userId);
        userDao.updatePassword(userId,
                PasswordUtils.createPassword(password, PasswordUtils.getUserSalt(user)));
        userDao.markTokenUsed(userToken.getId());
        return Result.SUCCESS;
    }

    @Override
    public UserDetail getUserDetail(long userId) {
        User user = userDao.getUserById(userId);
        UserDetail detail = userDao.getUserDetailById(userId);
        ReflectionUtils.copyPropertiesIgnoreNull(user, detail);

        long teamId = userTeamDao.getUserFavoriteTeamId(userId);
        detail.setFavoriteTeamId(teamId);
        detail.setFavoriteTeam(teamDao.getTeamById(teamId));

        return detail;
    }

    @Override
    @Transactional
    public Result updateDetail(UserDetail detail) {
        userDao.updateUser(detail);
        userDao.updateUserDetail(detail);
        if (detail.getFavoriteTeamId() != null) {
            userTeamDao.updateUserFavoriteClub(detail.getId(), detail.getFavoriteTeamId());
        }
        return Result.SUCCESS;
    }

    @Override
    public boolean isCountryManager(long userId) {
        User user = userDao.getUserById(userId);
        return user.getType() == User.USER_TYPE.COUNTRY_MANAGER;
    }

    @Override
    public boolean emailRegistered(String email) {
        return userDao.emailRegistered(email);
    }

    public boolean userNameRegistered(String userName) {
        return userDao.userNameRegistered(userName);
    }

    @Override
    @Transactional
    public Result inviteUser(User user) {
        if (emailRegistered(user.getEmail())) {
            return Result.OCCUPIED;
        }
        User recommender = userDao.getUserById(user.getOperatorId());
        if (recommender.getType().equals(User.USER_TYPE.END_USER)) {
            return Result.PERMISSION_DENIED;
        }
        // note that the code is not necessarily to be used by the user.email,
        // it can be shared to other email too, but can only be used once.
        InviteCode inviteCode = createInviteCode(recommender);

        // send email
        sendInviteEmail(user.getEmail(), inviteCode.getCode(), recommender.getEmail());

        return Result.SUCCESS;
    }

    @Override
    @Transactional
    public InviteCode createInviteCode(User invitor) {
        int inviteCode = genInvitationCode();
        InviteCode code = new InviteCode();
        code.setType(invitor.getType());  // the code's highest type can only equal to recommender's type
        code.setCode(inviteCode);
        code.setInvitorId(invitor.getId());
        inviteCodeDao.insertInviteCode(code);

        return code;
    }

    @Override
    @Transactional
    public Result signup(InvitedUserForm userForm) {
        if (userForm.getCode() == null) {
            return Result.PERMISSION_DENIED;
        }
        InviteCode code;
        try {
            code = inviteCodeDao.getInviteCode(Ints.tryParse(userForm.getCode()));
        } catch (DataAccessException e) {
            return Result.PERMISSION_DENIED;
        }
        User user = userForm.toUser();
        if (code.isUsed() || user.getType().ordinal() > code.getType().ordinal()) {
            return Result.PERMISSION_DENIED;
        }
        if (userNameRegistered(user.getUserName())
                || emailRegistered(user.getEmail())) {
            return Result.OCCUPIED;
        }
        createNewUser(user);

        // mark this code as used
        code.setEmail(userForm.getEmail());
        inviteCodeDao.update(code);

        // Update the invitor's invite number, and the invitor may get rewarded by inviting a new user
        UserRecord invitorRecord = userDao.getUserRecordById(code.getInvitorId());
        invitorRecord.setInvites(invitorRecord.getInvites() + 1);
        userDao.updateUserRecord(invitorRecord);
        rewardService.tryReward(Trigger.INVITE, code.getInvitorId(), user.getType(), invitorRecord.getInvites());

        return Result.SUCCESS;
    }

    @Override
    public User getUser(long userId) {
        return userDao.getUserById(userId);
    }

    @Override
    @Transactional
    public Result uploadAvatar(long userId, InputStream file, String fileName) {
        repositoryService.putFile(BasePath.USERS, getAvatarPath(userId), fileName, file);
        //userDao.updateAvatarUrl(userId, fileName);
        return Result.SUCCESS;
    }

    @Override
    public String getCompleteAvatarUrl(long userId, String avatarUrl) {
        if (Strings.isNullOrEmpty(avatarUrl)) {
            User user = getUser(userId);
            String urlEscaped = UrlEscapers.urlPathSegmentEscaper().escape(DEFAULT_AVATAR_URL);
            return GRAVATAR_URL.concat(SecurityUtils.encoderByMd5(user.getEmail()))
                    .concat("?")
                    .concat(Joiner.on("&").withKeyValueSeparator("=")
                            .join(ImmutableMap.of("s", "250", "d", urlEscaped)));
        }

        return repositoryService.getPath(BasePath.USERS, getAvatarPath(userId), avatarUrl);
    }

    @Override
    public boolean isInvitationCodeValid(String code) {
        InviteCode inviteCode;
        try {
            inviteCode = inviteCodeDao.getInviteCode(Ints.tryParse(code));
        } catch (Exception e) {
            return false;
        }

        return !inviteCode.isUsed();
    }

    /**
     * create a new user
     *
     * @param user
     */
    private void createNewUser(User user) {
        user.setCreateTime(TimeUtils.getCurrentTimeAbortMillis());

        // create password, use md5 hashed createTime as salt
        user.setPassword(PasswordUtils.createPassword(
                user.getPassword(), PasswordUtils.getUserSalt(user)));
        long userId = userDao.insertBasicTables(user);
        user.setId(userId);

        // create activate token and send an email
        String activateToken = createToken(userId);
        sendActivateEmail(user, activateToken);

        // get reward by sign up
        rewardService.tryReward(Trigger.SIGNUP, userId, user.getType());
    }

    /**
     * generate a random invitation code that not exist yet.
     *
     * @return
     */
    private int genInvitationCode() {
        Random random = new Random(System.currentTimeMillis());
        final int bound = 100000000;
        int code;
        do {
            code = random.nextInt(bound);
        } while (inviteCodeDao.codeExists(code));
        return code;
    }

    /**
     * create a token for user.
     *
     * @param userId
     * @return the token generated
     */
    private String createToken(long userId) {
        String token = SecurityUtils.genToken();
        Timestamp expires = new Timestamp(System.currentTimeMillis() + ONE_DAY_MILLS);
        userDao.insertUserToken(userId, token, expires);
        return token;
    }

    /**
     * UserToken outdate or not
     *
     * @param token
     * @return
     */
    private boolean tokenOutdate(UserToken token) {
        return token.getExpires().getTime() <= System.currentTimeMillis();
    }

    /**
     * send an activation email
     *
     * @param user
     * @param activateToken
     */
    private void sendActivateEmail(User user, String activateToken) {
        final String link = String.format("http://%s/account/%d/activate/%s/",
                websiteLink, user.getId(), activateToken);
        sendEmail(user.getEmail(), "Fan2Fan User Activate", link, "mail/activate_user");
    }

    /**
     * send a reset password email
     *
     * @param user
     * @param token
     */
    private void sendResetPwdEmail(User user, String token) {
        final String link = String.format("http://%s/account/%d/resetpassword/%s/",
                websiteLink, user.getId(), token);
        sendEmail(user.getEmail(), "Fan2Fan User Reset Password",
                link, "mail/changepassword");
    }

    /**
     * send an invitation email
     *
     * @param targetEmail
     * @param inviteCode
     * @param srcEmail
     */
    private void sendInviteEmail(String targetEmail, int inviteCode, String srcEmail) {
        final Context ctx = new Context();
        final String link = String.format("http://%s/account/signup/", websiteLink);
        ctx.setVariables(ImmutableMap.of(
                "link", link,
                "code", inviteCode,
                "from", srcEmail,
                "to", targetEmail));

        final String content = emailTemplateEngine.process("mail/inviteuser", ctx);
        emailService.sendEmail(targetEmail, "PartnerFan Invitation", content);
    }

    private void sendEmail(String email, String title, String link, String template) {
        final Context ctx = new Context();
        ctx.setVariable("link", link);
        final String content = emailTemplateEngine.process(template, ctx);
        emailService.sendEmail(email, title, content);
    }

    /**
     * return the user's avatar folder
     *
     * @param userId
     * @return
     */
    private String getAvatarPath(long userId) {
        return String.format("%d/avatar", userId);
    }
}
