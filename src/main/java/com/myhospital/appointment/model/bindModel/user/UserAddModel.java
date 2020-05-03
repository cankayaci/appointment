package com.myhospital.appointment.model.bindModel.user;

import com.myhospital.appointment.validator.IsUniqueUserName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserAddModel extends BaseUserModel {
    @NotBlank(message = "{lang.system.user.username.mandatory}")
    @IsUniqueUserName
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
