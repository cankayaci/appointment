package com.myhospital.appointment.model.bindModel.patient;

import com.myhospital.appointment.validator.IsUniquePatientIdentityNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PatientAddModel extends BasePatientModel {

    @IsUniquePatientIdentityNumber
    @Pattern(regexp = "^[0-9]{10,11}$", message = "{lang.patient.identity.invalid.format}")
    private String identityNumber;

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

}

