package com.myhospital.appointment.model.bindModel.user;

import com.myhospital.appointment.validator.IsUniqueUserName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EditUserModel extends BaseUserModel {

    private Integer id;

    @NotBlank(message = "{lang.system.user.username.mandatory}")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}