package com.fan2fan.web.views.account.controller;

import com.fan2fan.form.InvitedUserForm;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/account/signup/")
public class SignupController {

    public static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    private static final String SIGNUP_VIEW = "user/signup";
    private static final String SIGNUP_COMMAND = "signupCommand";
    private static final String SIGNUP_MSG = "signupMessage";
    private static final String BINDING_KEY = "org.springframework.validation.BindingResult.signupCommand";

    @Autowired
    private UserService userService;

    @ModelAttribute(SIGNUP_COMMAND)
    public InvitedUserForm signupCommand() {
        return new InvitedUserForm();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showPage(@RequestParam("code") String code,
                           @RequestParam("email") String email,
                           @ModelAttribute(SIGNUP_COMMAND) InvitedUserForm form,
                           HttpServletResponse response) throws IOException {
        if (!userService.isInvitationCodeValid(code)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        form.setEmail(email);
        form.setCode(code);

        return SIGNUP_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute(SIGNUP_COMMAND) InvitedUserForm user,
                         BindingResult bindingResult,
                         final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_KEY, bindingResult);
            redirectAttributes.addFlashAttribute(SIGNUP_COMMAND, user);
            return "redirect:/account/signup/";
        }
        Result result = userService.signup(user);
        if (result.equals(Result.SUCCESS)) {
            SessionManager.setUser(userService.getUserByEmail(user.getEmail()));
            return "redirect:/users/edit/";
        } else {
            redirectAttributes.addFlashAttribute(SIGNUP_MSG, result.toString());
            return "redirect:/account/signup/";
        }
    }

}
