package com.fan2fan.form;

import com.fan2fan.model.User;
import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.repeatPassword)", message = "two passwords must be matched")
public class SignupForm extends User {

    private String repeatPassword;

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    /**
     * the password and repeatPassword matches
     * @return password and repeatPassword match or not
     */
    public boolean pwdMatch() {
        return getPassword() != null && getPassword().equals(repeatPassword);
    }
}
