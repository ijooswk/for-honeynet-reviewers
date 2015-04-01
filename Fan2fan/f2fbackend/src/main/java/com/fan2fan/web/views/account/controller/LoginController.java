package com.fan2fan.web.views.account.controller;

import com.fan2fan.form.LoginForm;
import com.fan2fan.service.Result;
import com.fan2fan.service.user.UserService;
import com.fan2fan.web.Message;
import com.fan2fan.web.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account/login/")
public class LoginController {

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static final String LOGIN_COMMAND = "loginCommand";
    private static final String LOGIN_VIEW = "user/login";
    private static final String NEXT_URL_ATTR = "next";

    @Autowired
    private UserService userService;

    @ModelAttribute(LOGIN_COMMAND)
    public LoginForm getLoginForm() {
        return new LoginForm();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showPage(@ModelAttribute(LOGIN_COMMAND) LoginForm form,
                           @RequestParam(value = NEXT_URL_ATTR, required = false, defaultValue = "/") String next,
                           Model model) {
        if (SessionManager.isLogin()) {
            return "redirect:/home/";  // logged-in user not need to show the login page
        }
        model.addAttribute(NEXT_URL_ATTR, next);
        return LOGIN_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String signIn(@ModelAttribute("loginCommand") LoginForm form,
                         @RequestParam(value = NEXT_URL_ATTR, required = false, defaultValue = "/home/") String nextUrl,
                         final RedirectAttributes redirectAttributesModel) {
        Result result = userService.signin(form.getEmail(), form.getPassword());
        if (result.equals(Result.SUCCESS)) {
            SessionManager.setUser(userService.getUserByEmail(form.getEmail()));
            return "redirect:".concat(nextUrl);
        } else {
            redirectAttributesModel.addFlashAttribute(Message.KEY_MESSAGE, result.toString());
            return "redirect:/account/login/";
        }
    }
}
