package com.fan2fan.web.views.users.controller;

import com.fan2fan.form.ChangePasswordForm;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.Message;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Change account password
 *
 * @author huangsz
 */
@Controller
@RequestMapping(value = "/users/edit/password/")
public class ChangePasswordController {

    private static final String VIEW = "user/changePassword";

    private static final String CHANGE_PWD_COMMAND = "changePwdCommand";

    private static final String BINDING_KEY = "org.springframework.validation.BindingResult.changePwdCommand";

    @Autowired
    private UserService userService;

    @ModelAttribute(CHANGE_PWD_COMMAND)
    public ChangePasswordForm changePasswordForm() {
        return new ChangePasswordForm();
    }

    @LoginRequired
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showChangePasswordPage() {
        return VIEW;
    }

    @LoginRequired
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String changePassword(
            @Valid @ModelAttribute(CHANGE_PWD_COMMAND) ChangePasswordForm form,
            BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_KEY, bindingResult);
            redirectAttributes.addFlashAttribute(CHANGE_PWD_COMMAND, form);
            return "redirect:/users/edit/password/";
        }
        if (!form.getNewPassword().equals(form.getRepeatPassword())) {
            redirectAttributes.addFlashAttribute(Message.KEY_MESSAGE, "not_match");
        } else {
            Result result = userService.changePassword(SessionManager.getUserId(), form.getOldPassword(), form.getNewPassword());
            redirectAttributes.addFlashAttribute(Message.KEY_MESSAGE, result.toString());
        }
        return "redirect:/users/edit/password/";
    }

}

