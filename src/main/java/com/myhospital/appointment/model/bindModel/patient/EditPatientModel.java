package com.myhospital.appointment.model.bindModel.patient;

import com.myhospital.appointment.validator.IsUniquePatientIdentityNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EditPatientModel extends  BasePatientModel{

    private Integer id;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "{lang.patient.identity.invalid.format}")
    private String identityNumber;

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

