package com.fan2fan.web.views.users.controller;

import com.fan2fan.form.InvitationForm;
import com.fan2fan.model.User;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.intercept.LoginRequired;
import com.fan2fan.web.intercept.UserTypeAuth;
import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users/invite/")
public class InviteController {

    public static final Logger logger = LoggerFactory.getLogger(InviteController.class);

    public static final String INVITATION_COMMAND = "invitationCommand";

    @Autowired
    private UserService userService;

    @ModelAttribute("nav")
    public String nav() {
        return "invite";
    }

    @ModelAttribute(INVITATION_COMMAND)
    public InvitationForm invitationForm() {
        return new InvitationForm();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @UserTypeAuth
    @LoginRequired
    public String invitationPage() {

        return "user/invite";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @UserTypeAuth
    @LoginRequired
    public String invite(@Valid @ModelAttribute(INVITATION_COMMAND) InvitationForm form,
                         final RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setOperatorId(SessionManager.getUserId());
        logger.debug("user email is {}", form.getEmail());
        Result result = userService.inviteUser(user);
        logger.debug("result is {}", result);
        redirectAttributes.addFlashAttribute("message", result);

        return "redirect:/users/invite/";
    }
}
