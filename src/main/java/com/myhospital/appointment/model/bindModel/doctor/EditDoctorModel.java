package com.myhospital.appointment.model.bindModel.doctor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EditDoctorModel extends BaseDoctorModel {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
