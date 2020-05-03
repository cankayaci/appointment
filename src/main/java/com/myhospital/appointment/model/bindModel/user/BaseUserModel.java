package com.myhospital.appointment.model.bindModel.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BaseUserModel {

    @NotBlank(message = "{lang.system.user.password.mandatory}")
    @Size(min = 8, message ="{lang.system.user.password.min.eight}")
    private String password;

    @NotBlank(message="{lang.system.user.name.mandatory}")
    private String name;

    @NotBlank(message = "{lang.system.user.surname.mandatory}")
    private String surname;

    @Email(message = "{lang.system.user.email.not.valid}")
    private String email;

    private String roleName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
