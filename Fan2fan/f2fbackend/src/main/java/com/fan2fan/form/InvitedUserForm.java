package com.fan2fan.form;

import com.fan2fan.model.User;
import com.fan2fan.web.views.account.validator.CodeExist;
import com.fan2fan.web.views.account.validator.EmailExist;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Digits;

/**
 * @author huangsz
 */
@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.repeatPassword)", message = "two passwords must be matched")
public class InvitedUserForm {

    @NotBlank(message = "email address may not be empty")
    @Email(message = "not a well-formed email address")
    @EmailExist(message = "This email address is already in use")
    private String email;

    @NotBlank(message = "password may not be empty")
    @Length(min = 6, max = 30, message = "password length must be between {min} and {max}")
    private String password;

    private String repeatPassword;

    @NotBlank(message = "userName may not be empty")
    @Length(min = 3, max = 15, message = "userName length must be between {min} and {max}")
    private String userName;

    @NotBlank(message = "Invitation Code may not be empty.")
    @CodeExist(message = "Invitation Code is invalid.")
    @Digits(fraction = 0, integer = 8, message = "Invitation Code should be digits.")
    private String code;

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User toUser() {
        User user = new User();
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setUserName(this.getUserName());
        user.setType(User.USER_TYPE.PARTNER_FAN);
        user.setActivated(false);
        user.setFullName(this.getEmail().split("\\@")[0]);
        return user;
    }
}
