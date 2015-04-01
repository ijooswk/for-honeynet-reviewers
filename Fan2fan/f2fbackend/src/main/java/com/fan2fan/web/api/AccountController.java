package com.fan2fan.web.api;

import com.fan2fan.form.InvitedUserForm;
import com.fan2fan.form.UpdatePasswordForm;
import com.fan2fan.model.InviteCode;
import com.fan2fan.model.UserDetail;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import com.fan2fan.model.User;
import com.fan2fan.web.Message;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * Account and User Related Controller
 * @author huangsz .
 */
@RestController
@RequestMapping(value = "api/account")
public class AccountController {

    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    public ModelMap signup(@Valid @RequestBody User user) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, userService.signup(user).toString());
        return map;
    }

    @RequestMapping(value = "/{userId}/activate/{token}", method = RequestMethod.GET, produces = "application/json")
    public ModelMap activate(@PathVariable long userId, @PathVariable String token) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, userService.activate(userId, token).toString());
        map.put(Message.KEY_OBJECT, userService.getUser(userId));
        return map;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap signin(@RequestBody User user) {
        ModelMap map = new ModelMap();
        Result result = userService.signin(user.getEmail(), user.getPassword());
        map.put(Message.KEY_RESULT, result.toString());
        if (result.equals(Result.SUCCESS))
            map.put(Message.KEY_OBJECT, user);
        return map;
    }

    @RequestMapping(value="/{userId}/password", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap changePassword(
            @PathVariable int userId,
            @RequestBody UpdatePasswordForm updatePasswordUser) {
        ModelMap map = new ModelMap();
        // String test = CharStreams.toString(request.getReader());
        String oldPwd = updatePasswordUser.getPassword();
        String newPwd = updatePasswordUser.getNewPassword();
        map.put(Message.KEY_RESULT,
                userService.changePassword(userId, oldPwd, newPwd).toString());
        return map;
    }

    @RequestMapping(value="/forgetpassword", method = RequestMethod.POST, consumes ="application/json", produces="application/json")
    public ModelMap forgetPassword(@RequestBody User user) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT,
                userService.forgetPassword(user.getEmail()).toString());
        return map;
    }

    @RequestMapping(value = "/{userId}/resetpassword/{token}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap resetPassword(@RequestBody User user,
                                  @PathVariable long userId,
                                  @PathVariable String token) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT,
                userService.resetPassword(userId, token, user.getPassword()).toString());
        return map;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json")
    public UserDetail getUserDetail(@PathVariable long userId) {
        return userService.getUserDetail(userId);
    }

    @RequestMapping(value = "/{userId}", method  = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap updateUserDetail(@PathVariable long userId, @RequestBody UserDetail detail) {
        ModelMap map = new ModelMap();
        detail.setId(userId);
        map.put(Message.KEY_RESULT, userService.updateDetail(detail).toString());
        return map;
    }

    @RequestMapping(value = "/registered", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap checkUser(@RequestBody User user) {
        // checks only email for now
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, userService.emailRegistered(user.getEmail()));
        return map;
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap inviteUser(@RequestBody User user) {
        // the user.operatorId is the one who invites
        // the user.email is the one who is being invited
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, userService.inviteUser(user).toString());
        return map;
    }

    @RequestMapping(value = "/invite/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelMap inviteSignup(@RequestBody InvitedUserForm userForm) {
        ModelMap map = new ModelMap();
        map.put(Message.KEY_RESULT, userService.signup(userForm).toString());
        return map;
    }

    @LoginRequired
    @RequestMapping(value = "/invite/codes", method = RequestMethod.GET, produces = "application/json")
    public List<InviteCode> createInviteCodes() {
        List<InviteCode> codes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            codes.add(userService.createInviteCode(SessionManager.getUser()));
        }
        return codes;
    }

}
