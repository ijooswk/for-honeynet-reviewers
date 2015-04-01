package com.fan2fan.web.views.account.controller;

import com.fan2fan.form.ResetPasswordForm;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/account/{userId}/resetpassword/{token}/")
public class ResetPasswordController {

    public static final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    private static final String RESET_VIEW = "user/reset";
    private static final String RESET_COMMAND = "resetCommand";

    @Autowired
    private UserService userService;

    @ModelAttribute(RESET_COMMAND)
    public ResetPasswordForm getResetPasswordForm() {
        return new ResetPasswordForm();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showPage(@ModelAttribute(RESET_COMMAND) ResetPasswordForm form,
                           @PathVariable long userId,
                           @PathVariable String token,
                           HttpServletResponse response,
                           Model model) throws IOException {
        if (!userService.authenticatePasswordToken(userId, token)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        form.setUserId(userId);
        form.setToken(token);
        model.addAttribute("email", userService.getUser(userId).getEmail());

        return RESET_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute(RESET_COMMAND) ResetPasswordForm form,
                                @PathVariable long userId,
                                @PathVariable String token,
                                HttpServletResponse response) throws IOException {
        if (!(userId == form.getUserId() && form.getToken().equals(token))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        Result result = userService.resetPassword(userId, token, form.getPassword());

        if (!result.isSuccess()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return "redirect:/account/login/";
    }
}
