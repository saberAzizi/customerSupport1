package com.saber.site.model;

import com.saber.site.validations.NotBlank;

public class LoginForm {
    private String username;
    private String password;
    @NotBlank(message = "{validate.user.username}")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @NotBlank(message = "{validate.user.password}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
